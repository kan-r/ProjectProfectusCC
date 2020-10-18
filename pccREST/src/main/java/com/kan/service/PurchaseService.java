package com.kan.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kan.dao.PurchaseDao;
import com.kan.exception.InvalidDataException;
import com.kan.model.Category;
import com.kan.model.Product;
import com.kan.model.Purchase;

@Service
@Transactional
public class PurchaseService {
	
	Logger logger = LogManager.getLogger(PurchaseService.class);

	@Autowired
	private PurchaseDao purchaseDao;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<Purchase> getPurchaseListByLimit(int limit) {
		logger.info("getPurchaseList({})", limit);
		
		if(limit <= 0) {
			return null;
		}
		
		Page<Purchase> pageFirst = purchaseDao.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "purchId")));
		List<Purchase> purchListR = pageFirst.getContent();
		
		List<Purchase> purchList = new ArrayList<Purchase>();
		
		for (int i = purchListR.size() - 1; i >= 0; i--) {
			purchList.add(purchListR.get(i));
		}
		
		return purchList;
	}

	public Purchase addPurchase(Purchase purch) throws InvalidDataException {
		logger.info("add({})", purch);
		
		if(purch.getProdCode().isBlank()) {
			throw new InvalidDataException("Product Code is required");
		}
		
		if(purch.getCategory().isBlank()) {
			throw new InvalidDataException("Category is required");
		}
		
		if(purch.getPurchPrice() <= 0) {
			throw new InvalidDataException("Purchase Price must be a positive value");
		}
		
		if(purch.getPurchQty() <= 0) {
			throw new InvalidDataException("Quantity Purchased must be a positive value");
		}
		
		if(purch.getPurchDate() == null) {
			throw new InvalidDataException("Date of Purchase is required");
		}
	
		Category cat = new Category();
		cat.setCategory(purch.getCategory());
		cat = categoryService.addCategoryIfNotExists(cat);
		
		Product prod = new Product();
		prod.setProdCode(purch.getProdCode());
		prod.setProdName(purch.getProdName());
		prod.setPurchPrice(purch.getPurchPrice());
		prod.setCatId(cat.getCatId());
		
		prod = productService.upsertProduct(prod);
		
		return purchaseDao.save(purch);
	}
}
