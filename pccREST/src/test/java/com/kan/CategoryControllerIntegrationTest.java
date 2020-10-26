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
import com.kan.model.Category;

@SpringBootTest
@AutoConfigureMockMvc
//to use test (in memory H2) database
@AutoConfigureTestDatabase
class CategoryControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CategoryDao categoryDao;

	@BeforeEach
	void setUp() throws Exception {
		// given
		categoryDao.save(new Category(1, "Dairy"));
		categoryDao.save(new Category(2, "Fruit"));
	}

	@AfterEach
	void tearDown() throws Exception {
		categoryDao.deleteAll();
	}

	@Test
	void getCategoryList() throws Exception {

		mvc.perform(MockMvcRequestBuilders
				.get("/categories")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].category").value("Dairy"));
	}

}
