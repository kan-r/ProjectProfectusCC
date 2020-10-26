package com.kan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import com.kan.dao.ProductDao;
import com.kan.exception.InvalidDataException;
import com.kan.model.Product;

//testing using MockBeans
//all the dependencies of ProductService must be provided as MockBeans, otherwise it will be provided by the Spring and will use actual database
@SpringBootTest
class ProductServiceTest {
	
	@Autowired
	private ProductService productService;
	
	@MockBean
	private ProductDao productDao;

	@Test
	void testGetProductList() {
		List<Product> prodList = new ArrayList<Product>();
		prodList.add(new Product("1234", "Milk", 0, 1, null));
		prodList.add(new Product("4531", "Bananas", 0, 2, null));
		
		when(productDao.findAll(Sort.by("prodCode"))).thenReturn(prodList);
		assertThat(productService.getProductList()).hasSize(2);
	}

	@Test
	void testGetProduct_Exists() {
		Optional<Product> optProd = Optional.of(new Product("1234", "Milk", 0, 1, null));
		
		when(productDao.findById("1234")).thenReturn(optProd);
		assertThat(productService.getProduct("1234").getProdName()).isEqualTo("Milk");
	}
	
	@Test
	void testGetProduct_NotExists() {
		Optional<Product> optEmpty = Optional.empty();
		
		when(productDao.findById("4531")).thenReturn(optEmpty);
		assertThat(productService.getProduct("4531")).isEqualTo(null);
	}

	@Test
	void testAddProductIfNotExists() {
		Product prod = new Product("1234", "Milk", 0, 1, null);
		
		Optional<Product> optEmpty = Optional.empty();
		Optional<Product> optProd = Optional.of(prod);
		
		//if exists
		when(productDao.findById("1234")).thenReturn(optProd);
		assertThat(productService.addProductIfNotExists(prod).getProdCode()).isEqualTo("1234");
				
		//if not exists
		when(productDao.findById("1234")).thenReturn(optEmpty);
		when(productDao.save(prod)).thenReturn(prod);
		assertThat(productService.addProductIfNotExists(prod).getProdCode()).isEqualTo("1234");
	}

	@Test
	void testUpsertProduct_Exists_Price_Same() {
		Product prod1 = new Product("1234", "Milk", 500, 1, null);
		Product prod2 = new Product("1234", "Milk", 500, 1, null);
		
		Optional<Product> optProd1 = Optional.of(prod1);
		
		when(productDao.findById("1234")).thenReturn(optProd1);
		when(productDao.save(prod2)).thenReturn(prod1);
		
		try {
			assertThat(productService.upsertProduct(prod2).getProdCode()).isEqualTo("1234");
		} catch (InvalidDataException e) {
			// this shoudn't happen
			fail("Unexpected exception: " + e.getMessage());
		}
	}
	
	@Test
	void testUpsertProduct_Exists_Price_Zero() {
		Product prod1 = new Product("1234", "Milk", 0, 1, null);
		Product prod2 = new Product("1234", "Milk", 500, 1, null);
		
		Optional<Product> optProd1 = Optional.of(prod1);
		
		when(productDao.findById("1234")).thenReturn(optProd1);
		when(productDao.save(prod1)).thenReturn(prod2);
		
		try {
			assertThat(productService.upsertProduct(prod2).getPurchPrice()).isEqualTo(500);
		} catch (InvalidDataException e) {
			// this shoudn't happen
			fail("Unexpected exception: " + e.getMessage());
		}
	}
	
	@Test
	void testUpsertProduct_Exists_Price_Different() {
		Product prod1 = new Product("1234", "Milk", 400, 1, null);
		Product prod2 = new Product("1234", "Milk", 500, 1, null);
		
		Optional<Product> optProd1 = Optional.of(prod1);
		
		when(productDao.findById("1234")).thenReturn(optProd1);
		
		Exception exception = assertThrows(InvalidDataException.class, () -> {
			productService.upsertProduct(prod2);
	    });
		
		assertThat(exception.getMessage()).isEqualTo("Product cannot have a different Purchase Price");
	}
	
	@Test
	void testUpsertProduct_NotExists() {
		Product prod = new Product("1234", "Milk", 500, 1, null);
		Optional<Product> optEmpty = Optional.empty();
		
		when(productDao.findById("1234")).thenReturn(optEmpty);
		when(productDao.save(prod)).thenReturn(prod);
		
		try {
			assertThat(productService.upsertProduct(prod).getProdCode()).isEqualTo("1234");
		} catch (InvalidDataException e) {
			// this shoudn't happen
			fail("Unexpected exception: " + e.getMessage());
		}
	}

}
