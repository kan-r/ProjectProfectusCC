package com.kan.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kan.exception.InvalidDataException;
import com.kan.model.Profit;
import com.kan.service.ProfitService;

@RestController
@RequestMapping("/profits")
public class ProfitController {

	@Autowired
	private ProfitService profitService;
	
	@GetMapping(path = "", params = {"catId", "prodCodes", "priceFrom", "priceTo", "dateFrom", "dateTo"})
	public Profit getProfit(@RequestParam Map<String,String> params) throws InvalidDataException{
		return profitService.getProfit(params);
	}
}
