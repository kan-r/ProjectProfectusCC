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
import com.kan.model.Purchase;
import com.kan.service.PurchaseService;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;
	
	@GetMapping(path="", params = "limit")
	public List<Purchase> getPurchaseListByLimit(@RequestParam int limit){
		return purchaseService.getPurchaseListByLimit(limit);
	}

	@PostMapping("")
	public Purchase addPurchase(@RequestBody Purchase purchase) throws InvalidDataException{
		return purchaseService.addPurchase(purchase);
	}
	
}
