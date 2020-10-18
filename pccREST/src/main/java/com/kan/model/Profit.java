package com.kan.model;

public class Profit {

	private String type;
	private double amount;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "Profit [type=" + type + ", amount=" + amount + "]";
	}	
}
