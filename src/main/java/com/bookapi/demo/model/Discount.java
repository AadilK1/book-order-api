package com.bookapi.demo.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "tos_discounts",
uniqueConstraints = {@UniqueConstraint(columnNames = {"couponCode","classification"})}) //Combination of bookType and couponCode must be unique
public class Discount {
	
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonProperty(access = Access.READ_ONLY)
	private long id;
	
	@NonNull
	private String couponCode;
	
	@NonNull
	private BigDecimal percentageDiscount;
	
	@NonNull
	private String classification;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="couponcode")
	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	
	

	@Column(name="classification")
	public String getClassification() {
		return classification;
	}

	@Column(name="percentagediscount")
	public BigDecimal getPercentageDiscount() {
		return percentageDiscount;
	}

	public void setPercentageDiscount(BigDecimal percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

    
	
}
