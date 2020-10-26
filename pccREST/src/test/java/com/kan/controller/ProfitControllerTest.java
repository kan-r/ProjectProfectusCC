package com.kan.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.kan.model.Profit;
import com.kan.service.ProfitService;

@WebMvcTest(ProfitController.class)
class ProfitControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ProfitService profitService;

	@Test
	void testGetProfit_Profit() throws Exception {
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("catId", "");
		params.put("prodCodes", "1234,2345");
		params.put("priceFrom", "200");
		params.put("priceTo", "500");
		params.put("dateFrom", "");
		params.put("dateTo", "");
		
		Profit profit = new Profit("Profit", 7200.00);
		
		when(profitService.getProfit(params)).thenReturn(profit);
		
		mvc.perform( MockMvcRequestBuilders
			      .get("/profits")
			      .param("catId", "")
			      .param("prodCodes", "1234,2345")
			      .param("priceFrom", "200")
			      .param("priceTo", "500")
			      .param("dateFrom", "")
			      .param("dateTo", "")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.type").value("Profit"))
			      .andExpect(jsonPath("$.amount").value(7200.00));
	}
	
	@Test
	void testGetProfit_Loss() throws Exception {
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("catId", "");
		params.put("prodCodes", "2345");
		params.put("priceFrom", "290");
		params.put("priceTo", "290");
		params.put("dateFrom", "");
		params.put("dateTo", "");
		
		Profit profit = new Profit("Loss", 750.00);
		
		when(profitService.getProfit(params)).thenReturn(profit);
		
		mvc.perform( MockMvcRequestBuilders
			      .get("/profits")
			      .param("catId", "")
			      .param("prodCodes", "2345")
			      .param("priceFrom", "290")
			      .param("priceTo", "290")
			      .param("dateFrom", "")
			      .param("dateTo", "")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.type").value("Loss"))
			      .andExpect(jsonPath("$.amount").value(750.00));
	}
}
