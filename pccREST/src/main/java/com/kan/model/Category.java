package com.kan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cat_id", unique = true, nullable = false)
	private int catId;
	
	@Column(name = "category", nullable = false)
	private String category;
	

	public Category() {
		super();
	}
	
	public Category(String category) {
		super();
		this.category = category;
	}
	
	public Category(int catId, String category) {
		super();
		this.catId = catId;
		this.category = category;
	}


	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Category [catId=" + catId + ", category=" + category + "]";
	}
	
}
