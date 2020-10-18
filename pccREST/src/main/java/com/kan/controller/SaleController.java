package com.kan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kan.exception.InvalidDataException;
import com.kan.model.Sale;
import com.kan.service.SaleService;

@RestController
@RequestMapping("/sales")
public class SaleController {
	
	@Autowired
	private SaleService saleService;
	
	@GetMapping(path="", params = "limit")
	public List<Sale> getSaleListByLimit(@RequestParam int limit){
		return saleService.getSaleListByLimit(limit);
	}

	@PostMapping("")
	public Sale add(@RequestBody Sale sale) throws InvalidDataException{
		return saleService.addSale(sale);
	}

}
