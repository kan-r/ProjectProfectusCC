package com.kan;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ProfitControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private SaleService saleService;

//	@Test
	void getProfit() throws Exception {
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("catId", "");
		params.put("prodCodes", "1234,2345");
		params.put("priceFrom", "200");
		params.put("priceTo", "500");
		params.put("dateFrom", "");
		params.put("dateTo", "");
		
		List<Sale> sales = new ArrayList<Sale>();
		sales.add(new Sale("1234", 180, 540.0, new Product("1234", 500.0)));
		sales.add(new Sale("2345", 75, 300.0, new Product("2345", 300.0)));
		
		when(saleService.getSaleListByCiteria(params)).thenReturn(sales);
		
		mvc.perform( MockMvcRequestBuilders
			      .get("/profits")
			      .param("catId", "")
			      .param("prodCodes", "1234,2345")
			      .param("priceFrom", "200")
			      .param("priceTo", "200")
			      .param("dateFrom", "")
			      .param("dateTo", "")
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.type").value("Profit"))
			      .andExpect(jsonPath("$.amount").value(7200.00));
	}
}
