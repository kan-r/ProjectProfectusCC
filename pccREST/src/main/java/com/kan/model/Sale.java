package com.kan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="sales")
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sales_id", unique = true, nullable = false)
	private int salesId;
	
	@Column(name = "prod_code")
	private String prodCode;
	
	@Temporal(TemporalType.DATE)
    @Column(name="sales_date", length=7)
    private Date salesDate;
	
	@Column(name = "sales_qty")
	private int salesQty;
	
	@Column(name = "sales_price")
	private double salesPrice;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=true)
	@JoinColumn(name = "prod_code", insertable = false, updatable = false)
	private Product product;
	
	@Transient
	private String category;
	
	@Transient
	private String prodName;
	
	
	public Sale() {
		super();
	}
	
	public Sale(String prodCode, int salesQty, double salesPrice, Product product) {
		super();
		this.prodCode = prodCode;
		this.salesQty = salesQty;
		this.salesPrice = salesPrice;
		this.product = product;
	}
	
	public Sale(int salesId, String prodCode, Date salesDate, int salesQty, double salesPrice, Product product,
			String category, String prodName) {
		super();
		this.salesId = salesId;
		this.prodCode = prodCode;
		this.salesDate = salesDate;
		this.salesQty = salesQty;
		this.salesPrice = salesPrice;
		this.product = product;
		this.category = category;
		this.prodName = prodName;
	}

	
	public int getSalesId() {
		return salesId;
	}

	public void setSalesId(int salesId) {
		this.salesId = salesId;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public int getSalesQty() {
		return salesQty;
	}

	public void setSalesQty(int salesQty) {
		this.salesQty = salesQty;
	}

	public double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
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
		return "Sale [salesId=" + salesId + ", prodCode=" + prodCode + ", salesDate=" + salesDate + ", salesQty="
				+ salesQty + ", salesPrice=" + salesPrice + ", category=" + category + ", prodName=" + prodName + "]";
	}
}
