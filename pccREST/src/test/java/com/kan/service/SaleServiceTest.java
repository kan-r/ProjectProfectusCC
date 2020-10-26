package com.kan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.kan.exception.InvalidDataException;
import com.kan.model.Category;
import com.kan.model.Product;
import com.kan.model.Sale;
import com.kan.util.GenUtils;

// testing using in memory (H2) database
// if you add another dependency to SaleService, this will fail, you need to provide bean under @TestConfiguration
@DataJpaTest
class SaleServiceTest {
	
	@Autowired
	private SaleService saleService;
	
	@Autowired
    private TestEntityManager testEntityManager;
	
	// SaleService and all the dependencies except SaleDao
	@TestConfiguration
    static class SaleServiceTestConfiguration {
		
		@Bean
        public SaleService saleService() {
            return new SaleService();
        }
		
		@Bean
        public CategoryService categoryService() {
            return new CategoryService();
        }
		
		@Bean
        public ProductService productService() {
            return new ProductService();
        }
    }

	@BeforeEach
	void setUp() throws Exception {
		int catId1 = testEntityManager.persist(new Category("Dairy")).getCatId();
		int catId2 = testEntityManager.persist(new Category("Fruit")).getCatId();
		
		testEntityManager.persist( new Product("4531", "Bananas", 0, catId2, null));
		testEntityManager.persist( new Product("2345", "Cream", 0, catId1, null));
		testEntityManager.persist( new Product("1234", "Milk", 0, catId1, null));
		
		testEntityManager.persist(new Sale("1234", GenUtils.toDate("2020-06-09"), 200, 500.0, "Dairy", "Milk"));
		testEntityManager.persist(new Sale("1234", GenUtils.toDate("2020-07-18"), 100, 520.0, "Dairy", "Milk"));
		testEntityManager.persist(new Sale("2345", GenUtils.toDate("2020-06-12"), 100, 300.0, "Dairy", "Cream"));
		testEntityManager.persist(new Sale("4531", GenUtils.toDate("2020-07-18"), 400, 200.0, "Fruit", "Bananas"));
	}
	
	@Test
	void testGetPurchaseListByLimit_0() {
		assertThat(saleService.getSaleListByLimit(0)).isEqualTo(null);
	}

	@Test
	void testGetSaleListByLimit_2() {
		int limit = 2;
		List<Sale> saleList = saleService.getSaleListByLimit(limit);
		
		assertThat(saleList.size()).isEqualTo(limit);
		assertThat(saleList.get(0).getProdCode()).isEqualTo("2345");
		assertThat(saleList.get(1).getProdCode()).isEqualTo("4531");
	}

	@Test
	void testGetSaleListByCiteria() {
//		params -> catId=1&prodCodes=1234,2345&priceFrom=200&priceTo=300&dateFrom=2020-06-14&dateTo=2020-07-23
		Map<String,String> params = new HashMap<String,String>();
		params.put("catId", "");
		params.put("prodCodes", "1234,4531");
		params.put("priceFrom", "");
		params.put("priceTo", "");
		params.put("dateFrom", "");
		params.put("dateTo", "");
		
		assertThat(saleService.getSaleListByCiteria(params)).hasSize(3);
		
		params.put("dateTo", "2020-07-18");
		assertThat(saleService.getSaleListByCiteria(params)).hasSize(3);
		
		params.put("dateFrom", "2020-07-18");
		assertThat(saleService.getSaleListByCiteria(params)).hasSize(2);
	}
	
	@Test
	void testAddSale() {
		
		Sale sale = new Sale("2345", GenUtils.toDate("2020-10-18"), 100, 300.0, "Dairy", "Cream");
		
		try {
			assertThat(saleService.addSale(sale).getProdCode()).isEqualTo("2345");
		} catch (InvalidDataException e) {
			// this shoudn't happen
			fail("Unexpected exception: " + e.getMessage());
		}
	}
	
	@Test
	void testAddSale_ProdCode_Empty() {	
		
		Sale sale = new Sale("", GenUtils.toDate("2020-10-18"), 100, 300.0, "Dairy", "Cream");
		
		Exception exception = assertThrows(InvalidDataException.class, () -> {
			saleService.addSale(sale);
	    });
		
		assertThat(exception.getMessage()).isEqualTo("Product Code is required");
	}
	
	@Test
	void testAddSale_Category_Empty() {	
		
		Sale sale = new Sale("2345", GenUtils.toDate("2020-10-18"), 100, 300.0, "", "Cream");
		
		Exception exception = assertThrows(InvalidDataException.class, () -> {
			saleService.addSale(sale);
	    });
		
		assertThat(exception.getMessage()).isEqualTo("Category is required");
	}
	
	@Test
	void testAddSale_PurchPrice_Zero() {	
		
		Sale sale = new Sale("2345", GenUtils.toDate("2020-10-18"), 100, 0, "Dairy", "Cream");
		
		Exception exception = assertThrows(InvalidDataException.class, () -> {
			saleService.addSale(sale);
	    });
		
		assertThat(exception.getMessage()).isEqualTo("Sale Price must be a positive value");
	}
	
	@Test
	void testAddSale_PurchQty_Zero() {	
		
		Sale sale = new Sale("2345", GenUtils.toDate("2020-10-18"), 0, 300.0, "Dairy", "Cream");
		
		Exception exception = assertThrows(InvalidDataException.class, () -> {
			saleService.addSale(sale);
	    });
		
		assertThat(exception.getMessage()).isEqualTo("Quantity Sold must be a positive value");
	}
	
	@Test
	void testAddSale_PurchDate_Empty() {	
		
		Sale sale = new Sale("2345", null, 100, 300.0, "Dairy", "Cream");
		
		Exception exception = assertThrows(InvalidDataException.class, () -> {
			saleService.addSale(sale);
	    });
		
		assertThat(exception.getMessage()).isEqualTo("Date of Sale is required");
	}

}
