package com.kan.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;

import com.kan.model.Category;
import com.kan.model.Product;

//testing using in memory (H2) database
@DataJpaTest
class ProductDaoTest {
	
	@Autowired
    private TestEntityManager testEntityManager;
	
	@Autowired
    private ProductDao productDao;

	@BeforeEach
	void setUp() throws Exception {
		int catId1 = testEntityManager.persist(new Category("Dairy")).getCatId();
		int catId2 = testEntityManager.persist(new Category("Fruit")).getCatId();
		
		testEntityManager.persist( new Product("4531", "Bananas", 200.00, catId2, null));
		testEntityManager.persist( new Product("2345", "Cream", 300.00, catId1, null));
	}

	@Test
	void testFindAllSort() {
		List<Product> prodList = productDao.findAll(Sort.by("prodCode"));
		
		assertThat(prodList).hasSize(2);
		assertThat(prodList.get(0).getProdCode()).isEqualTo("2345");
	}
	
	@Test
	void testFindById_Exists() {
		
		String prodCode = "4531";
		Product prod = null;
		Optional<Product> opt = productDao.findById(prodCode);
		if (opt.isPresent()) {
			prod = opt.get();
		}
		
		assertThat(prod.getProdCode()).isEqualTo("4531");
	}
	
	@Test
	void testFindById_NotExists() {
		
		String prodCode = "1234";
		Product prod = null;
		Optional<Product> opt = productDao.findById(prodCode);
		if (opt.isPresent()) {
			prod = opt.get();
		}
		
		assertThat(prod).isEqualTo(null);
	}

	@Test
	void testSave() {
		Product prod = new Product("2134", "Cheese", 0, 1, null);
		
		assertThat(productDao.save(prod).getProdName()).isEqualTo("Cheese");
	}

}
