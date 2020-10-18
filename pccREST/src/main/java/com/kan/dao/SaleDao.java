package com.kan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kan.model.Sale;

public interface SaleDao extends JpaRepository<Sale, Integer> {

}
