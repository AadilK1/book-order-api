package com.bookapi.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookapi.demo.exception.ResourceNotFoundException;
import com.bookapi.demo.model.Discount;
import com.bookapi.demo.service.DiscountRepositoryService;

@RestController
@RequestMapping(value="/api/v1")
public class DiscountCRUDController {
	
    @Autowired
    private DiscountRepositoryService discountsRepository;

    @GetMapping(value="/discounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        return ResponseEntity.ok(discountsRepository.findAll());
    }

    @GetMapping(value="/discount/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Discount> getDiscountsById(@PathVariable(value = "id") Long discountsId)
    throws ResourceNotFoundException {
        Discount discountsObj = discountsRepository.findById(discountsId)
        		.orElseThrow(() -> new ResourceNotFoundException("discount not found for this id :: " + discountsId));
        
        
        return ResponseEntity.ok().body(discountsObj);
    }

    @PostMapping(value="/discount",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Discount> createDiscounts(@Valid @RequestBody Discount Discounts) {
    	Discount DiscountsresponseObj=discountsRepository.saveAndFlush(Discounts);
        return ResponseEntity.ok(DiscountsresponseObj);
    }

    @PutMapping(value="/discount/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Discount> updateDiscounts(@PathVariable(value = "id") Long discountsId,
        @Valid @RequestBody Discount discountsRequest) throws ResourceNotFoundException {
        
        discountsRepository.findById(discountsId)
        		.orElseThrow(() -> new ResourceNotFoundException("discount not found for this id :: " + discountsId));

        discountsRequest.setId(discountsId);
        
        final Discount updatedDiscountsDetails = discountsRepository.save(discountsRequest);
        return ResponseEntity.ok(updatedDiscountsDetails);
    }

    @DeleteMapping(value="/discount/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map <String, Boolean> deleteDiscounts(@PathVariable(value = "id") Long discountsId) throws ResourceNotFoundException {

    	  //Check for Discount existence
        Discount discountsObj=discountsRepository.findById(discountsId)
        			.orElseThrow(() -> new ResourceNotFoundException("discount not found for this id :: " + discountsId));
        
        
         discountsRepository.delete(discountsObj);
        
        Map <String,Boolean> response = new HashMap <> ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
    
}
