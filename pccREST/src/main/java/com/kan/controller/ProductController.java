package com.kan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kan.model.Product;
import com.kan.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("")
	public List<Product> getProductList(){
		return productService.getProductList();
	}
	
	@GetMapping("/{id}")
	public Product getProduct(@PathVariable String id){
		return productService.getProduct(id);
	}
	
}
