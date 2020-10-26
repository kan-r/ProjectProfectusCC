package com.kan;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.kan.dao.CategoryDao;
import com.kan.dao.ProductDao;
import com.kan.dao.SaleDao;
import com.kan.model.Category;
import com.kan.model.Product;
import com.kan.model.Sale;
import com.kan.util.GenUtils;

@SpringBootTest
@AutoConfigureMockMvc
//to use test (in memory H2) database
@AutoConfigureTestDatabase
class SaleControllerIntegrationTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private SaleDao saleDao;

	@BeforeEach
	void setUp() throws Exception {
		int catId1 = categoryDao.save(new Category("Dairy")).getCatId();
		int catId2 = categoryDao.save(new Category("Fruit")).getCatId();
		
		productDao.save(new Product("4531", "Bananas", 290, catId2, null));
		productDao.save(new Product("2345", "Cream", 300, catId1, null));
		productDao.save(new Product("1234", "Milk", 500, catId1, null));
		
		saleDao.save(new Sale("1234", GenUtils.toDate("2020-06-09"), 180, 540.0, "Dairy", "Milk"));
		saleDao.save(new Sale("2345", GenUtils.toDate("2020-06-12"), 75, 300.0, "Dairy", "Cream"));
		saleDao.save(new Sale("4531", GenUtils.toDate("2020-07-18"), 100, 250.0, "Fruit", "Bananas"));
	}

	@AfterEach
	void tearDown() throws Exception {
		saleDao.deleteAll();
		productDao.deleteAll();
		categoryDao.deleteAll();
	}

	@Test
	void testGetSaleListByLimit() throws Exception {
		
		mvc.perform( MockMvcRequestBuilders
			      .get("/sales").param("limit", "2")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", Matchers.hasSize(2)))
			      .andExpect(jsonPath("$[0].prodCode").value("2345"))
			      .andExpect(jsonPath("$[1].product.prodCode").value("4531"));
	}

//	@Test
	void testAddSale() throws Exception {
	
		String purchJson = "{\"prodCode\":\"1234\",\"purchDate\":\"2020-10-18\",\"purchQty\":100,\"purchPrice\":540.0,\"category\":\"Dairy\",\"prodName\":\"Milk\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/sales")
				.content(purchJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.prodCode").value("1234"));
	}

}
