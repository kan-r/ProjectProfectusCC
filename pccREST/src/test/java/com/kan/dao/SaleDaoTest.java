package com.kan.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.kan.model.Category;
import com.kan.model.Product;
import com.kan.model.Sale;
import com.kan.util.GenUtils;

//testing using in memory (H2) database
@DataJpaTest
class SaleDaoTest {
	
	@Autowired
    private TestEntityManager testEntityManager;
	
	@Autowired
    private SaleDao saleDao;

	@BeforeEach
	void setUp() throws Exception {
		int catId1 = testEntityManager.persist(new Category("Dairy")).getCatId();
		int catId2 = testEntityManager.persist(new Category("Fruit")).getCatId();
		
		testEntityManager.persist( new Product("4531", "Bananas", 0, catId2, null));
		testEntityManager.persist( new Product("2345", "Cream", 0, catId1, null));
		testEntityManager.persist( new Product("1234", "Milk", 0, catId1, null));
		
		testEntityManager.persist(new Sale("1234", GenUtils.toDate("2020-06-09"), 200, 500.0, "Dairy", "Milk"));
		testEntityManager.persist(new Sale("2345", GenUtils.toDate("2020-06-12"), 100, 300.0, "Dairy", "Cream"));
		testEntityManager.persist(new Sale("4531", GenUtils.toDate("2020-07-18"), 400, 200.0, "Fruit", "Bananas"));
	}

	@Test
	void testFindAllPageable_limit_2() {
		int limit = 2;
		Page<Sale> pageFirst = saleDao.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "salesId")));
		List<Sale> saleList = pageFirst.getContent();
		
		assertThat(saleList.size()).isEqualTo(2);
		assertThat(saleList.get(0).getProdCode()).isEqualTo("4531");
		assertThat(saleList.get(1).getProdCode()).isEqualTo("2345");
	}
	
	@Test
	void testFindAllPageable_limit_3() {
		int limit = 3;
		Page<Sale> pageFirst = saleDao.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "salesId")));
		List<Sale> saleList = pageFirst.getContent();
		
		assertThat(saleList.size()).isEqualTo(3);
		assertThat(saleList.get(0).getProdCode()).isEqualTo("4531");
		assertThat(saleList.get(1).getProdCode()).isEqualTo("2345");
	}
	
	@Test
	void testFindAllPageable_limit_4() {
		int limit = 4;
		Page<Sale> pageFirst = saleDao.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "salesId")));
		List<Sale> saleList = pageFirst.getContent();
		
		assertThat(saleList.size()).isEqualTo(3);
		assertThat(saleList.get(0).getProdCode()).isEqualTo("4531");
		assertThat(saleList.get(1).getProdCode()).isEqualTo("2345");
	}
	
	@Test
	void testSave_Product_Exists() {
		Sale sale = new Sale("2345", GenUtils.toDate("2020-10-18"), 100, 300.0, "Dairy", "Cream");
		Sale saleS = saleDao.save(sale);
		
		assertThat(saleS.getProdCode()).isEqualTo("2345");
		assertThat(saleS.getSalesDate()).isEqualTo(GenUtils.toDate("2020-10-18"));
	}

	@Test
	void testSave_Product_NotExists() {
		Sale sale = new Sale("2134", GenUtils.toDate("2020-10-18"), 100, 700.0, "Dairy", "Cheese");
		
		Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
			saleDao.save(sale);
	    });
		
		assertThat(exception.getMessage()).contains("ConstraintViolationException");
	}
}
