package com.kan.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;

import com.kan.model.Category;

//testing using in memory (H2) database
@DataJpaTest
class CategoryDaoTest {
	
	@Autowired
    private TestEntityManager testEntityManager;
	
	@Autowired
    private CategoryDao categoryDao;

	@BeforeEach
	void setUp() throws Exception {
		testEntityManager.persist(new Category("Fruit"));
		testEntityManager.persist(new Category("Dairy"));
	}

	@Test
	void testFindByCategory() {
		assertThat(categoryDao.findByCategory("Fruit").getCategory()).isEqualTo("Fruit");
	}

	@Test
	void testFindAllSort() {
		List<Category> catList = categoryDao.findAll(Sort.by("category"));
		
		assertThat(catList).hasSize(2);
		assertThat(catList.get(0).getCategory()).isEqualTo("Dairy");
	}

}
