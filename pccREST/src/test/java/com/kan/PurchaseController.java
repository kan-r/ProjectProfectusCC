package com.kan;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.kan.model.Product;
import com.kan.model.Purchase;
import com.kan.service.PurchaseService;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseController {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private PurchaseService purchService;
	
	@Test
	void getPurchaseListByLimit() throws Exception {
		
		List<Purchase> purchs = new ArrayList<Purchase>();
		purchs.add(new Purchase("1234", 180, 540.0, new Product("1234", 500.0)));
		purchs.add(new Purchase("2345", 75, 300.0, new Product("2345", 300.0)));
		
		when(purchService.getPurchaseListByLimit(2)).thenReturn(purchs);
		
		mvc.perform( MockMvcRequestBuilders
			      .get("/purchases").param("limit", "2")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", Matchers.hasSize(2)))
			      .andExpect(jsonPath("$[0].prodCode").value("1234"))
			      .andExpect(jsonPath("$[1].product.prodCode").value("2345"));
	}

}
