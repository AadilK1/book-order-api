package com.bookapi.demo.model;

import java.util.List;

public class Order {
	
	private List<Long> bookIds;
	
	private String couponCodeUsed;

	public List<Long> getBookIds() {
		return bookIds;
	}

	public void setBookIds(List<Long> bookIds) {
		this.bookIds = bookIds;
	}

	public String getCouponCodeUsed() {
		return couponCodeUsed;
	}

	public void setCouponCodeUsed(String couponCodeUsed) {
		this.couponCodeUsed = couponCodeUsed;
	}
	
	
	
}
