package com.kan.model;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="purchases")
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purch_id", unique = true, nullable = false)
	private int purchId;
	
	@Column(name = "prod_code")
	private String prodCode;
	
//	handled by com.kan.util.LocalDateAttributeConverter
//	@Temporal(TemporalType.DATE)
    @Column(name="purch_date", length=7)
    private LocalDate purchDate;
	
	@Column(name = "purch_qty")
	private int purchQty;
	
	@Column(name = "purch_price")
	private double purchPrice;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name = "prod_code", insertable = false, updatable = false)
	private Product product;
	
	@Transient
	private String category;
	
	@Transient
	private String prodName;
	
	

	public Purchase() {
		super();
	}
	
	public Purchase(String prodCode, int purchQty, double purchPrice, Product product) {
		super();
		this.prodCode = prodCode;
		this.purchQty = purchQty;
		this.purchPrice = purchPrice;
		this.product = product;
	}
	
	public Purchase(String prodCode, LocalDate purchDate, int purchQty, double purchPrice, String category, String prodName) {
		super();
		this.prodCode = prodCode;
		this.purchDate = purchDate;
		this.purchQty = purchQty;
		this.purchPrice = purchPrice;
		this.category = category;
		this.prodName = prodName;
	}

	public Purchase(int purchId, String prodCode, LocalDate purchDate, int purchQty, double purchPrice, Product product,
			String category, String prodName) {
		super();
		this.purchId = purchId;
		this.prodCode = prodCode;
		this.purchDate = purchDate;
		this.purchQty = purchQty;
		this.purchPrice = purchPrice;
		this.product = product;
		this.category = category;
		this.prodName = prodName;
	}
	

	public int getPurchId() {
		return purchId;
	}

	public void setPurchId(int purchId) {
		this.purchId = purchId;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public LocalDate getPurchDate() {
		return purchDate;
	}

	public void setPurchDate(LocalDate purchDate) {
		this.purchDate = purchDate;
	}

	public int getPurchQty() {
		return purchQty;
	}

	public void setPurchQty(int purchQty) {
		this.purchQty = purchQty;
	}

	public double getPurchPrice() {
		return purchPrice;
	}

	public void setPurchPrice(double purchPrice) {
		this.purchPrice = purchPrice;
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	@Override
	public String toString() {
		return "Purchase [purchId=" + purchId + ", prodCode=" + prodCode + ", purchDate=" + purchDate + ", purchQty="
				+ purchQty + ", purchPrice=" + purchPrice + ", product=" + product + ", category=" + category
				+ ", prodName=" + prodName + "]";
	}
}
