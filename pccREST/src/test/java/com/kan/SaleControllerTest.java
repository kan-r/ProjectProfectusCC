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
import com.kan.model.Sale;
import com.kan.service.SaleService;

@SpringBootTest
@AutoConfigureMockMvc
public class SaleControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private SaleService saleService;
	
	@Test
	void getProducList() throws Exception {
		
		List<Sale> sales = new ArrayList<Sale>();
		sales.add(new Sale("1234", 180, 540.0, new Product("1234", 500.0)));
		sales.add(new Sale("2345", 75, 300.0, new Product("2345", 300.0)));
		
		when(saleService.getSaleListByLimit(2)).thenReturn(sales);
		
		mvc.perform( MockMvcRequestBuilders
			      .get("/sales").param("limit", "2")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$", Matchers.hasSize(2)))
			      .andExpect(jsonPath("$[0].prodCode").value("1234"))
			      .andExpect(jsonPath("$[1].product.prodCode").value("2345"));
	}
	
}
