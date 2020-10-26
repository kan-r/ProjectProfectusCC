package com.kan.controller;

import static org.mockito.ArgumentMatchers.any;
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

import com.kan.model.Product;
import com.kan.model.Purchase;
import com.kan.service.PurchaseService;

@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PurchaseService purchService;

	@Test
	void testGetPurchaseListByLimit() throws Exception {
		
		List<Purchase> purchs = new ArrayList<Purchase>();
		purchs.add(new Purchase("1234", 180, 540.0, new Product("1234", 500.0)));
		purchs.add(new Purchase("2345", 75, 300.0, new Product("2345", 300.0)));
		
		when(purchService.getPurchaseListByLimit(2)).thenReturn(purchs);
		
		mvc.perform( MockMvcRequestBuilders
			      .get("/purchases?limit=2")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", Matchers.hasSize(2)))
			      .andExpect(jsonPath("$[0].prodCode").value("1234"))
			      .andExpect(jsonPath("$[1].product.prodCode").value("2345"));
	}

	@Test
	void testAddPurchase() throws Exception {
		
		Purchase purch = new Purchase("1234", null, 100, 500.0, "Dairy", "Milk");
		when(purchService.addPurchase(any(Purchase.class))).thenReturn(purch);
		
		String purchJson = "{\"prodCode\":\"1234\",\"purchDate\":\"2020-10-18\",\"purchQty\":100,\"purchPrice\":500.0,\"category\":\"Dairy\",\"prodName\":\"Milk\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/purchases")
				.content(purchJson)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.prodCode").value("1234"));
	}

}
