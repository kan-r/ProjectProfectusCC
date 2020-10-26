package com.kan.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kan.dao.SaleDao;
import com.kan.exception.InvalidDataException;
import com.kan.model.Category;
import com.kan.model.Product;
import com.kan.model.Sale;
import com.kan.util.GenUtils;

@Service
@Transactional
public class SaleService {

	Logger logger = LogManager.getLogger(SaleService.class);

	@Autowired
	private SaleDao saleDao;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<Sale> getSaleListByLimit(int limit) {
		logger.info("getSaleListByLimit({})", limit);
		
		if(limit <= 0) {
			return null;
		}
		
		Page<Sale> pageFirst = saleDao.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "salesId")));
		List<Sale> saleListR = pageFirst.getContent();
		
		List<Sale> saleList = new ArrayList<Sale>();
		
		for (int i = saleListR.size() - 1; i >= 0; i--) {
			saleList.add(saleListR.get(i));
		}
		
		return saleList;
	}
	
	public List<Sale> getSaleListByCiteria(Map<String,String> params) {
		logger.info("getSaleListByCiteria({})", params);
		
//		params -> catId=1&prodCodes=1234,2345&priceFrom=200&priceTo=300&dateFrom=2020-06-14&dateTo=2020-07-23
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sale> cq = cb.createQuery(Sale.class);
		Root<Sale> sale = cq.from(Sale.class);
		
		List<Predicate> pred = new ArrayList<>();
	
		if(!params.get("catId").isEmpty()) {
			int catId = GenUtils.toInt(params.get("catId"));
			pred.add(cb.equal(sale.get("product").get("catId"), catId));
		}
		
		if(!params.get("prodCodes").isEmpty()) {
			List<String> prodCodes = GenUtils.toArrayList(params.get("prodCodes"));
			pred.add(sale.get("prodCode").in(prodCodes));
		}
		
		if(!params.get("priceFrom").isEmpty()) {
			double priceFrom = GenUtils.toDouble(params.get("priceFrom"));
			pred.add(cb.greaterThanOrEqualTo(sale.get("product").get("purchPrice"), priceFrom));
		}
		
		if(!params.get("priceTo").isEmpty()) {
			double priceTo = GenUtils.toDouble(params.get("priceTo"));
			pred.add(cb.lessThanOrEqualTo(sale.get("product").get("purchPrice"), priceTo));
		}
		
		if(!params.get("dateFrom").isEmpty()) {
			LocalDate dateFrom = GenUtils.toDate(params.get("dateFrom"));
			pred.add(cb.greaterThanOrEqualTo(sale.get("salesDate"), dateFrom));
		}
		
		if(!params.get("dateTo").isEmpty()) {
			LocalDate dateTo = GenUtils.toDate(params.get("dateTo"));
			pred.add(cb.lessThanOrEqualTo(sale.get("salesDate"), dateTo));
		}
		
		cq.where(pred.toArray(new Predicate[pred.size()]));
		
        TypedQuery<Sale> query = entityManager.createQuery(cq);
		
		return query.getResultList();
	}
	
	public Sale addSale(Sale sale) throws InvalidDataException {
		logger.info("addSale({})", sale);
		
		if(sale.getProdCode().isBlank()) {
			throw new InvalidDataException("Product Code is required");
		}
		
		if(sale.getCategory().isBlank()) {
			throw new InvalidDataException("Category is required");
		}
		
		if(sale.getSalesPrice() <= 0) {
			throw new InvalidDataException("Sale Price must be a positive value");
		}
		
		if(sale.getSalesQty() <= 0) {
			throw new InvalidDataException("Quantity Sold must be a positive value");
		}
		
		if(sale.getSalesDate() == null) {
			throw new InvalidDataException("Date of Sale is required");
		}
		
		Category cat = new Category();
		cat.setCategory(sale.getCategory());
		cat = categoryService.addCategoryIfNotExists(cat);
		
		Product prod = new Product();
		prod.setProdCode(sale.getProdCode());
		prod.setProdName(sale.getProdName());
		prod.setPurchPrice(0);
		prod.setCatId(cat.getCatId());
		
		prod = productService.addProductIfNotExists(prod);
		
		return saleDao.save(sale);
	}
}
