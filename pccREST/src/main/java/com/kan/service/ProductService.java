package com.kan.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kan.dao.ProductDao;
import com.kan.exception.InvalidDataException;
import com.kan.model.Product;

@Service
@Transactional
public class ProductService {

	Logger logger = LogManager.getLogger(ProductService.class);

	@Autowired
	private ProductDao productDao;

	public List<Product> getProductList() {
		logger.info("getProductList()");
		return productDao.findAll();
	}

	public Product getProduct(String prodCode) {
		logger.info("getProduct({})", prodCode);
		
		Optional<Product> opt = productDao.findById(prodCode);
		if (opt.isPresent()) {
			return opt.get();
		}

		return null;
	}
	
	public Product addProductIfNotExists(Product product) {
		logger.info("add({})", product);
		
		Product prod = getProduct(product.getProdCode());
		if(prod != null) {
			return prod;
		}
		
		return productDao.save(product);
	}
	
	public Product upsertProduct(Product product) throws InvalidDataException {
		logger.info("upsert({})", product);
		
		Product prod = getProduct(product.getProdCode());
		if(prod != null) {
			if(prod.getPurchPrice() == product.getPurchPrice()) {
				return prod;
			}
			
			if(prod.getPurchPrice() == 0) {
				prod.setPurchPrice(product.getPurchPrice());
				return productDao.save(prod);
			}
			
			throw new InvalidDataException("Product cannot have a different Purchase Price");
		}
		
		return productDao.save(product);
	}
}
