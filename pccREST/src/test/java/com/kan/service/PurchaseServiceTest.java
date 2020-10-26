package com.kan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.kan.dao.PurchaseDao;
import com.kan.exception.InvalidDataException;
import com.kan.model.Category;
import com.kan.model.Product;
import com.kan.model.Purchase;
import com.kan.util.GenUtils;

// testing using MockBeans
// all the dependencies of PurchaseService must be provided as MockBeans, otherwise it will be provided by the Spring and will use actual database
@SpringBootTest
class PurchaseServiceTest {
	
	@Autowired
	private PurchaseService purchaseService;
	
	@MockBean
	private PurchaseDao purchaseDao;
	
	@MockBean
	private CategoryService categoryService;
	
	@MockBean
	private ProductService productService;

	@Test
	void testGetPurchaseListByLimit_0() {
		assertThat(purchaseService.getPurchaseListByLimit(0)).isEqualTo(null);
	}
	
	void testGetPurchaseListByLimit_2() {
		List<Purchase> purchs = new ArrayList<Purchase>();
		purchs.add(new Purchase("2345", 75, 300.0, new Product("2345", 300.0)));
		purchs.add(new Purchase("1234", 180, 540.0, new Product("1234", 500.0)));
		
		int limit = 2;
		Page<Purchase> page = new PageImpl<>(purchs);
		when(purchaseDao.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "purchId")))).thenReturn(page);
		
		List<Purchase> purchList = purchaseService.getPurchaseListByLimit(limit);
		
		assertThat(purchList.size()).isEqualTo(limit);
		assertThat(purchList.get(0).getProdCode()).isEqualTo("1234");
		assertThat(purchList.get(1).getProdCode()).isEqualTo("2345");
	}

	@Test
	void testAddPurchase() {
		Category cat = new Category(1, "Dairy");
		when(categoryService.addCategoryIfNotExists(any(Category.class))).thenReturn(cat);
		
		Product prod = new Product("2345", "Cream", 0, 1, null);
		
		try {
			when(productService.upsertProduct(any(Product.class))).thenReturn(prod);
		} catch (InvalidDataException e) {
			// this shoudn't happen
			fail("Unexpected exception: " + e.getMessage());
		}
		
		Purchase purch = new Purchase("2345", GenUtils.toDate("2020-10-18"), 100, 300.0, "Dairy", "Cream");
		when(purchaseDao.save(purch)).thenReturn(purch);
		
		try {
			assertThat(purchaseService.addPurchase(purch).getProdCode()).isEqualTo("2345");
		} catch (InvalidDataException e) {
			// this shoudn't happen
			fail("Unexpected exception: " + e.getMessage());
		}
	}
	
	@Test
	void testAddPurchase_ProdCode_Empty() {	
		
		Purchase purch = new Purchase("", GenUtils.toDate("2020-10-18"), 100, 300.0, "Dairy", "Cream");
		
		Exception exception = assertThrows(InvalidDataException.class, () -> {
			purchaseService.addPurchase(purch);
	    });
		
		assertThat(exception.getMessage()).isEqualTo("Product Code is required");
	}
	
	@Test
	void testAddPurchase_Category_Empty() {	
		
		Purchase purch = new Purchase("2345", GenUtils.toDate("2020-10-18"), 100, 300.0, "", "Cream");
		
		Exception exception = assertThrows(InvalidDataException.class, () -> {
			purchaseService.addPurchase(purch);
	    });
		
		assertThat(exception.getMessage()).isEqualTo("Category is required");
	}
	
	@Test
	void testAddPurchase_PurchPrice_Zero() {	
		
		Purchase purch = new Purchase("2345", GenUtils.toDate("2020-10-18"), 100, 0, "Dairy", "Cream");
		
		Exception exception = assertThrows(InvalidDataException.class, () -> {
			purchaseService.addPurchase(purch);
	    });
		
		assertThat(exception.getMessage()).isEqualTo("Purchase Price must be a positive value");
	}
	
	@Test
	void testAddPurchase_PurchQty_Zero() {	
		
		Purchase purch = new Purchase("2345", GenUtils.toDate("2020-10-18"), 0, 300.0, "Dairy", "Cream");
		
		Exception exception = assertThrows(InvalidDataException.class, () -> {
			purchaseService.addPurchase(purch);
	    });
		
		assertThat(exception.getMessage()).isEqualTo("Quantity Purchased must be a positive value");
	}
	
	@Test
	void testAddPurchase_PurchDate_Empty() {	
		
		Purchase purch = new Purchase("2345", null, 100, 300.0, "Dairy", "Cream");
		
		Exception exception = assertThrows(InvalidDataException.class, () -> {
			purchaseService.addPurchase(purch);
	    });
		
		assertThat(exception.getMessage()).isEqualTo("Date of Purchase is required");
	}

}
