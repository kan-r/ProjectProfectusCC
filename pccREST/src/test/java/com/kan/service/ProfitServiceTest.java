package com.kan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.kan.exception.InvalidDataException;
import com.kan.model.Product;
import com.kan.model.Profit;
import com.kan.model.Sale;

//testing using MockBeans
//all the dependencies of ProfitService must be provided as MockBeans, otherwise it will be provided by the Spring and will use actual database
@SpringBootTest
class ProfitServiceTest {
	
	@Autowired
	private ProfitService profitService;
	
	@MockBean
	private SaleService saleService;

	@Test
	void getProfit_NoSales() throws InvalidDataException {
		
		Map<String,String> params = new HashMap<String,String>();
		
		List<Sale> sales = new ArrayList<Sale>();
		
		when(saleService.getSaleListByCiteria(params)).thenReturn(sales);
		
		String expectedType = "Profit";
		double expectedAmount = 0;
		Profit actual = profitService.getProfit(params);
		
		assertThat(actual.getType()).isEqualTo(expectedType);
		assertThat(actual.getAmount()).isEqualTo(expectedAmount);
	}
	
	@Test
	void getProfit_NoProfit() throws InvalidDataException {
		
		Map<String,String> params = new HashMap<String,String>();
		
		List<Sale> sales = new ArrayList<Sale>();
		sales.add(new Sale("1234", 180, 500.0, new Product("1234", 500.0)));
		sales.add(new Sale("2345", 75, 300.0, new Product("2345", 300.0)));
		
		when(saleService.getSaleListByCiteria(params)).thenReturn(sales);
		
		String expectedType = "Profit";
		double expectedAmount = 0;
		Profit actual = profitService.getProfit(params);
	
		assertThat(actual.getType()).isEqualTo(expectedType);
		assertThat(actual.getAmount()).isEqualTo(expectedAmount);
	}
	
	@Test
	void getProfit_Profit() throws InvalidDataException {
		
		Map<String,String> params = new HashMap<String,String>();
		
		List<Sale> sales = new ArrayList<Sale>();
		sales.add(new Sale("1234", 180, 540.0, new Product("1234", 500.0)));
		sales.add(new Sale("2345", 75, 300.0, new Product("2345", 300.0)));
		
		when(saleService.getSaleListByCiteria(params)).thenReturn(sales);
		
		String expectedType = "Profit";
		double expectedAmount = 7200.00;
		Profit actual = profitService.getProfit(params);
		
		assertThat(actual.getType()).isEqualTo(expectedType);
		assertThat(actual.getAmount()).isEqualTo(expectedAmount);
	}
	
	@Test
	void getProfit_Loss() throws InvalidDataException {
		
		Map<String,String> params = new HashMap<String,String>();
		
		List<Sale> sales = new ArrayList<Sale>();
		sales.add(new Sale("2345", 75, 290.0, new Product("2345", 300.0)));
		
		when(saleService.getSaleListByCiteria(params)).thenReturn(sales);
		
		String expectedType = "Loss";
		double expectedAmount = 750.00;
		Profit actual = profitService.getProfit(params);

		assertThat(actual.getType()).isEqualTo(expectedType);
		assertThat(actual.getAmount()).isEqualTo(expectedAmount);
	}
}
