package com.kan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kan.model.Purchase;

public interface PurchaseDao extends JpaRepository<Purchase, Integer> {

}
