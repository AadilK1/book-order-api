package com.bookapi.demo.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookapi.demo.model.Discount;

public interface DiscountRepositoryService extends JpaRepository<Discount, Long>{
	
	List<Discount> findByCouponCode(String couponCode);

}
