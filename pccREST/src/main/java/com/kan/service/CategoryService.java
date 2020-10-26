package com.kan.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kan.dao.CategoryDao;
import com.kan.model.Category;

@Service
@Transactional
public class CategoryService {
	
	Logger logger = LogManager.getLogger(CategoryService.class);
	
	@Autowired
	private CategoryDao categoryDao;

	public List<Category> getCategoryList(){
		logger.info("getCategoryList()");
		return categoryDao.findAll(Sort.by("category"));
	}
	
	public Category addCategoryIfNotExists(Category category){
		logger.info("addCategoryIfNotExists({})", category);
		
		Category cat = categoryDao.findByCategory(category.getCategory());
		if(cat != null) {
			return cat;
		}
		
		return categoryDao.save(category);
	}
}
