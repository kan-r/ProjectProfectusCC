package com.kan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kan.model.Product;

public interface ProductDao extends JpaRepository<Product, String> {

}
