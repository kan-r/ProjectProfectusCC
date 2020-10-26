package com.kan.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.kan.model.Category;

//testing using in memory (H2) database
//if you add another dependency to CategoryService, this will fail, you need to provide bean under @TestConfiguration
@DataJpaTest
class CategoryServiceTest3 {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
    private TestEntityManager testEntityManager;
	
	@TestConfiguration
    static class CategoryServiceConfiguration {
 
        @Bean
        public CategoryService categoryService() {
            return new CategoryService();
        }
    }
	
	@BeforeEach
	void setUp() throws Exception {
		 // given
		testEntityManager.persist(new Category("Dairy")).getCatId();
		testEntityManager.persist(new Category("Fruit")).getCatId();
		testEntityManager.flush();
	}

	@Test
	void testGetCategoryList() {
		assertThat(categoryService.getCategoryList()).hasSize(2);
	}

	@Test
	void testAddCategoryIfNotExists() {
		
		Category cat1 = new Category("Dairy");
		Category cat2 = new Category("Nuts");
		
		//if exists
		assertThat(categoryService.addCategoryIfNotExists(cat1).getCategory()).isEqualTo("Dairy");
		
		//if not exists
		assertThat(categoryService.addCategoryIfNotExists(cat2).getCategory()).isEqualTo("Nuts");
	}

}
