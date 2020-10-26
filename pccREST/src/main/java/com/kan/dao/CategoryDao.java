package com.kan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kan.model.Category;

public interface CategoryDao extends JpaRepository<Category, Integer>{

	public Category findByCategory(String category);
	
}
