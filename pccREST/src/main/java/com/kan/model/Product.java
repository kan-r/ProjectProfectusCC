package com.kan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="products")
public class Product {

	@Id 
    @Column(name = "prod_code", unique = true, nullable = false)
	private String prodCode;
	
	@Column(name = "prod_name")
	private String prodName;
	
	@Column(name = "purch_price")
	private double purchPrice;
	
	@Column(name = "cat_id")
	private int catId;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=true)
	@JoinColumn(name = "cat_id", insertable = false, updatable = false)
	private Category category;
	
	
	public Product() {
		
	}
	
	public Product(String prodCode, double purchPrice) {
		super();
		this.prodCode = prodCode;
		this.purchPrice = purchPrice;
	}
	
	public Product(String prodCode, String prodName, double purchPrice, int catId, Category category) {
		super();
		this.prodCode = prodCode;
		this.prodName = prodName;
		this.purchPrice = purchPrice;
		this.catId = catId;
		this.category = category;
	}
	
	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public double getPurchPrice() {
		return purchPrice;
	}

	public void setPurchPrice(double purchPrice) {
		this.purchPrice = purchPrice;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [prodCode=" + prodCode + ", prodName=" + prodName + ", purchPrice=" + purchPrice + ", catId="
				+ catId + ", category=" + category + "]";
	}
}
