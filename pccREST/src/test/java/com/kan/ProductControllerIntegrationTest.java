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
import com.kan.model.Category;
import com.kan.model.Product;

@SpringBootTest
@AutoConfigureMockMvc
//to use test (in memory H2) database
@AutoConfigureTestDatabase
class ProductControllerIntegrationTest {
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private ProductDao productDao;
	
	@BeforeEach
	void setUp() throws Exception {
		int catId1 = categoryDao.save(new Category(1, "Dairy")).getCatId();
		int catId2 = categoryDao.save(new Category(2, "Fruit")).getCatId();
		
		productDao.save(new Product("4531", "Bananas", 200.00, catId2, null));
		productDao.save(new Product("2345", "Cream", 300.00, catId1, null));
	}

	@AfterEach
	void tearDown() throws Exception {
		productDao.deleteAll();
		categoryDao.deleteAll();
	}

	@Test
	void testGetProductList() throws Exception {
		
		mvc.perform(MockMvcRequestBuilders
				.get("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].prodCode").value("2345"));
	}

	@Test
	void testGetProduct() throws Exception {
		
		mvc.perform( MockMvcRequestBuilders
			      .get("/products/4531")
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.prodName").value("Bananas"))
			      .andExpect(jsonPath("$.purchPrice").value(200.00));
	}

}
