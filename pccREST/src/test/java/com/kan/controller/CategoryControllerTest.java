package com.kan.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.kan.model.Category;
import com.kan.service.CategoryService;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CategoryService categoryService;

	@Test
	void testGetCategoryList() throws Exception {
		
		List<Category> categoryList = new ArrayList<Category>();
		categoryList.add(new Category(1, "Dairy"));
		categoryList.add(new Category(2, "Fruit"));
		
		when(categoryService.getCategoryList()).thenReturn(categoryList);
		
		mvc.perform( MockMvcRequestBuilders
			      .get("/categories")
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", Matchers.hasSize(2)))
			      .andExpect(jsonPath("$[0].catId").value(1));
	}

}
