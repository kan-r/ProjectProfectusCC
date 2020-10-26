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
import com.kan.model.Product;
import com.kan.service.ProductService;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProductService productService;

	@Test
	void testGetProductList() throws Exception {
		
		List<Product> productList = new ArrayList<Product>();
		productList.add(new Product("4531", "Bananas", 200.00, 2, new Category(2, "Fruit")));
		productList.add(new Product("2345", "Cream", 300.00, 1, new Category(1, "Dairy")));
		
		when(productService.getProductList()).thenReturn(productList);
		
		mvc.perform( MockMvcRequestBuilders
			      .get("/products")
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", Matchers.hasSize(2)))
			      .andExpect(jsonPath("$[0].prodCode").value("4531"))
			      .andExpect(jsonPath("$[1].category.catId").value(1));
	}

	@Test
	void testGetProduct() throws Exception {

		Product product = new Product("4531", "Bananas", 200.00, 2, new Category(2, "Fruit"));
		
		when(productService.getProduct("4531")).thenReturn(product);
		
		mvc.perform( MockMvcRequestBuilders
			      .get("/products/{id}", "4531")
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.prodName").value("Bananas"))
			      .andExpect(jsonPath("$.purchPrice").value(200.00));
	}
}
