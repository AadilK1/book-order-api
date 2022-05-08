package com.bookapi.demo.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookapi.demo.exception.ResourceNotFoundException;
import com.bookapi.demo.model.Book;
import com.bookapi.demo.model.Discount;
import com.bookapi.demo.model.Order;
import com.bookapi.demo.service.BookRepositoryService;
import com.bookapi.demo.service.DiscountRepositoryService;

@RestController
@RequestMapping(value="/api/v1")
public class BookOrderController {
	
	
    @Autowired
    private BookRepositoryService bookRepository;
    
    @Autowired
    private DiscountRepositoryService discountRepositoryService;
	
    @PostMapping(value="/getCartValue",consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,BigDecimal>> getTotalCartValue(@Valid @RequestBody Order order) {
    	
    	Iterable<Book> bookIterable=bookRepository.findByIdIn(order.getBookIds());
    	
    	//if non existence book ids passed
       	if(bookIterable == null) {
    		new ResourceNotFoundException("Books not found for the supplied book ids");
    	}
    	
    	List<Book> bookslist=StreamSupport.stream(bookIterable.spliterator(), false).collect(Collectors.toList());
    	
    	//if non existence book ids passed
       	if(bookslist.size() != order.getBookIds().size()) {
    		new ResourceNotFoundException("Books not found for the supplied book ids");
    	}
    
    	//If Discount coupon code is passed ,calculate discount amount and update the price
    	if(null != order.getCouponCodeUsed()) {
    	 
    		List<Discount> discountObj=discountRepositoryService.findByCouponCode(order.getCouponCodeUsed());
    	
    		if(null == discountObj || discountObj.isEmpty()) {
    			new ResourceNotFoundException("Discount not found for this coupon code :: " + order.getCouponCodeUsed());
    		}
    		
    		Function<Book, Discount> discountMatched = bookObj -> discountObj.stream().filter(d -> d.getClassification().equals(bookObj.getType())).findFirst().orElse(null);
    		
    		
    		bookslist.
    		forEach(bookObj -> {
    		    if(discountMatched.apply(bookObj) != null){
    		    	bookObj.setPrice(bookObj.getPrice().subtract(bookObj.getPrice().multiply(discountMatched.apply(bookObj).getPercentageDiscount().divide(new BigDecimal(100.00)))) );
    		    }});
    		    
		
    	}
    	
 	    	
    	//Calculate total amount
        BigDecimal sum = bookslist.stream()
                .map(x -> x.getPrice())    // map
                .reduce(BigDecimal.ZERO, BigDecimal::add);      // reduce
    	
        Map<String,BigDecimal> totalCartValue=new HashMap<>();
        
        totalCartValue.put("cartvalue", sum);
        
        return ResponseEntity.ok(totalCartValue);
    }
	

}
