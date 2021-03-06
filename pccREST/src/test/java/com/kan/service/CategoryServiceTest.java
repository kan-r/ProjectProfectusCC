package com.kan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import com.kan.dao.CategoryDao;
import com.kan.model.Category;


//testing using MockBeans
//all the dependencies of CategoryService must be provided as MockBeans, otherwise it will be provided by the Spring and will use actual database
@SpringBootTest
class CategoryServiceTest {
	
	@Autowired
	private CategoryService categoryService;
	
	@MockBean
	private CategoryDao categoryDao;

	@Test
	void testGetCategoryList() {
		
		List<Category> categoryList = new ArrayList<Category>();
		categoryList.add(new Category(1, "Dairy"));
		categoryList.add(new Category(2, "Fruit"));
		
		when(categoryDao.findAll(Sort.by("category"))).thenReturn(categoryList);
		assertThat(categoryService.getCategoryList()).hasSize(2);
	}

	@Test
	void testAddCategoryIfNotExists() {
		
		Category cat1 = new Category(1, "Dairy");
		Category cat2 = new Category(2, "Dairy");
		
		//if exists
		when(categoryDao.findByCategory("Dairy")).thenReturn(cat1);
		assertThat(categoryService.addCategoryIfNotExists(cat1).getCatId()).isEqualTo(1);
		
		//if not exists
		when(categoryDao.findByCategory("Dairy")).thenReturn(null);
		when(categoryDao.save(cat1)).thenReturn(cat2);
		assertThat(categoryService.addCategoryIfNotExists(cat1).getCatId()).isEqualTo(2);
	}

}
