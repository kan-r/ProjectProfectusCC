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
import com.kan.model.Purchase;
import com.kan.util.GenUtils;

//testing using in memory (H2) database
@DataJpaTest
class PurchaseDaoTest {
	
	@Autowired
    private TestEntityManager testEntityManager;
	
	@Autowired
    private PurchaseDao purchaseDao;

	@BeforeEach
	void setUp() throws Exception {
		int catId1 = testEntityManager.persist(new Category("Dairy")).getCatId();
		int catId2 = testEntityManager.persist(new Category("Fruit")).getCatId();
		
		testEntityManager.persist( new Product("4531", "Bananas", 0, catId2, null));
		testEntityManager.persist( new Product("2345", "Cream", 0, catId1, null));
		testEntityManager.persist( new Product("1234", "Milk", 0, catId1, null));
		
		testEntityManager.persist(new Purchase("1234", GenUtils.toDate("2020-06-09"), 200, 500.0, "Dairy", "Milk"));
		testEntityManager.persist(new Purchase("2345", GenUtils.toDate("2020-06-12"), 100, 300.0, "Dairy", "Cream"));
		testEntityManager.persist(new Purchase("4531", GenUtils.toDate("2020-07-18"), 400, 200.0, "Fruit", "Bananas"));
	}

	@Test
	void testFindAllPageable_limit_2() {
		int limit = 2;
		Page<Purchase> pageFirst = purchaseDao.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "purchId")));
		List<Purchase> purchList = pageFirst.getContent();
		
		assertThat(purchList.size()).isEqualTo(2);
		assertThat(purchList.get(0).getProdCode()).isEqualTo("4531");
		assertThat(purchList.get(1).getProdCode()).isEqualTo("2345");
	}
	
	@Test
	void testFindAllPageable_limit_3() {
		int limit = 3;
		Page<Purchase> pageFirst = purchaseDao.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "purchId")));
		List<Purchase> purchList = pageFirst.getContent();
		
		assertThat(purchList.size()).isEqualTo(3);
		assertThat(purchList.get(0).getProdCode()).isEqualTo("4531");
		assertThat(purchList.get(1).getProdCode()).isEqualTo("2345");
	}
	
	@Test
	void testFindAllPageable_limit_4() {
		int limit = 4;
		Page<Purchase> pageFirst = purchaseDao.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "purchId")));
		List<Purchase> purchList = pageFirst.getContent();
		
		assertThat(purchList.size()).isEqualTo(3);
		assertThat(purchList.get(0).getProdCode()).isEqualTo("4531");
		assertThat(purchList.get(1).getProdCode()).isEqualTo("2345");
	}

	@Test
	void testSave_Product_Exists() {
		Purchase purch = new Purchase("2345", GenUtils.toDate("2020-10-18"), 100, 300.0, "Dairy", "Cream");
		Purchase purchS = purchaseDao.save(purch);
		
		assertThat(purchS.getProdCode()).isEqualTo("2345");
		assertThat(purchS.getPurchDate()).isEqualTo(GenUtils.toDate("2020-10-18"));
	}
	
	@Test
	void testSave_Product_NotExists() {
		Purchase purch = new Purchase("2134", GenUtils.toDate("2020-10-18"), 100, 700.0, "Dairy", "Cheese");
		
		Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
			purchaseDao.save(purch);
	    });
		
		assertThat(exception.getMessage()).contains("ConstraintViolationException");
	}
}
