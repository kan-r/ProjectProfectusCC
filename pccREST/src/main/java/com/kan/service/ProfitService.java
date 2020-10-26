package com.kan.service;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kan.exception.InvalidDataException;
import com.kan.model.Profit;
import com.kan.model.Sale;

@Service
public class ProfitService {

	Logger logger = LogManager.getLogger(ProfitService.class);
	
	@Autowired
	private SaleService saleService;

	public Profit getProfit(Map<String,String> params) throws InvalidDataException {
		logger.info("getProfit({})", params);
		
		Profit profit = new Profit();
		
		String type = "Profit";
		double amount = 0.0;
		
		List<Sale> sales = saleService.getSaleListByCiteria(params);
		for (Sale sale : sales) {
			amount += sale.getSalesQty()*(sale.getSalesPrice() - sale.getProduct().getPurchPrice());
		}
	
		if(amount < 0) {
			type = "Loss";
			amount *= -1;
		}
		
		profit.setType(type);
		profit.setAmount(amount);
		
		return profit;
	}
	
}
