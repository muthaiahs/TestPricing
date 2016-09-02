package com.sl.test.pricing.domain;

import java.io.Serializable;

public class PricingFeed implements Serializable {

	private Long itemId; 
	private String itemName;
	private Double weight;
	private String desc;
	
	
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	} 
	
	
	
	
	
	
	
}
