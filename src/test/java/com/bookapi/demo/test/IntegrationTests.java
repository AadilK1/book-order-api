package com.bookapi.demo.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bookapi.demo.model.Book;
import com.bookapi.demo.model.Discount;
import com.bookapi.demo.model.Order;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTests {
	
	  @LocalServerPort
	  private int port;
	  
	  @Autowired
	  private TestRestTemplate restTemplate;

	  //This Method will test Book POST operation and GET operation
	  @Test
	  public void test1BookCrudPOSTOperationSuccess() {
	 
		//Test POST  
	    String posturl = "http://localhost:"+port+"/api/v1/book";
	 
	    Book bookRequest = new Book();
	    bookRequest.setAuthor("Author1");
	    bookRequest.setDescription("COMIC BOOK ADDED");
	    bookRequest.setIsbn("ISBN710");
	    bookRequest.setName("COMIC book");
	    bookRequest.setType("COMIC");
	    bookRequest.setPrice(new BigDecimal(1000.00));
	    ResponseEntity<Book> entity = this.restTemplate.postForEntity(posturl, bookRequest, Book.class);
	    Assertions.assertThat(entity.getBody() != null);
	    
	    
	    Book bookRequest2 = new Book();
	    bookRequest2.setAuthor("Author2");
	    bookRequest2.setDescription("FICTION BOOK ADDED");
	    bookRequest2.setIsbn("ISBN720");
	    bookRequest2.setName("FICTION book");
	    bookRequest2.setType("FICTION");
	    bookRequest2.setPrice(new BigDecimal(1000.00));
	    ResponseEntity<Book> entity2 = this.restTemplate.postForEntity(posturl, bookRequest2, Book.class);
	    Assertions.assertThat(entity2.getBody() != null);
	    
	    
	    //Test GET
	    String geturl = "http://localhost:"+port+"/api/v1/book/1";
	    
	    Book bookObj = this.restTemplate.getForObject(geturl, Book.class);
	    Assertions.assertThat(bookObj.getIsbn().equals("ISBN710"));
	  
	  }
	  
	  //This Method will test book PUT operation and verify updated object with GET operation
	  @Test
	  public void test2BookCrudPUTOperationSuccess() {
	  
	    //Test PUT
	    String puturl = "http://localhost:"+port+"/api/v1/book/1";
	    Book bookRequest = new Book();
	    bookRequest.setAuthor("New Author");
	    bookRequest.setDescription("NewBook by Author");
	    bookRequest.setIsbn("ISBN700");
	    bookRequest.setName("Title of book new");
	    bookRequest.setType("COMIC");
	    bookRequest.setPrice(new BigDecimal(500.00));
	    
	    
	    this.restTemplate.put(puturl, bookRequest);
		 
	    //Test GET
	    String geturl = "http://localhost:"+port+"/api/v1/book/1";
	    
	    Book bookObjAfterUpdate = this.restTemplate.getForObject(geturl, Book.class);
	    Assertions.assertThat(bookObjAfterUpdate.getIsbn().equals("ISBN700"));
	
	  }  
	  
	  
	  //This operation will Test all Discount crud operation except delete
	  @Test
	  public void test3AllDiscountsCrudOperationSuccess() {
	 
		//Test POST  
	    String posturl = "http://localhost:"+port+"/api/v1/discount";
	 
	    Discount dicountRequest = new Discount();
	    dicountRequest.setClassification("COMIC");
	    dicountRequest.setCouponCode("COM10");
	    dicountRequest.setPercentageDiscount(new BigDecimal(10.00));
	    
	    ResponseEntity<Discount> entity = this.restTemplate.postForEntity(posturl, dicountRequest, Discount.class);
	    Assertions.assertThat(entity != null);
	    
	    //Test GET
	    String geturl = "http://localhost:"+port+"/api/v1/discount/3";
	    
	    Discount discountObj = this.restTemplate.getForObject(geturl, Discount.class);
	    Assertions.assertThat(discountObj.getCouponCode().equals("COM10"));
	    
	  }
	  
	  //The Above added two Book of type comic and fiction and Discount of 10 percent on comic book will be applied
	  //book Added above are of prices Comic: 500 , FICTION: 1000 (Adding both COMIC and FICTION in cart and 10% Discount on Comic will cause the total cart value to 1450)
	  @Test
	  public void test4BookOrder() {
	 
		//Test POST  
	    String posturl = "http://localhost:"+port+"/api/v1/getCartValue";
	    
	    Order orderObj=new Order();
	    
	    List<Long> bookIdList=new ArrayList<Long>();
	    bookIdList.add(1l);
	    bookIdList.add(2l);
	    orderObj.setBookIds(bookIdList);
	    orderObj.setCouponCodeUsed("COM10");
	    
	    
	    ResponseEntity<Object> entity = this.restTemplate.postForEntity(posturl, orderObj, Object.class);
	    Map<String,Double> response=(Map<String, Double>) entity.getBody();
	    
	    Assertions.assertThat(response.get("cartvalue").equals(new Double("1450.00")));
	    
	    
	  }  
	  
	  
	  //Create Discount of 10 percent on FICTION type books as well
	  @Test
	  public void test5DiscountoperationSuccess() {
	 
		//Test POST  
	    String posturl = "http://localhost:"+port+"/api/v1/discount";
	 
	    Discount dicountRequest = new Discount();
	    dicountRequest.setClassification("FICTION");
	    dicountRequest.setCouponCode("COM10");
	    dicountRequest.setPercentageDiscount(new BigDecimal(10.00));
	    
	    ResponseEntity<Discount> entity = this.restTemplate.postForEntity(posturl, dicountRequest, Discount.class);
	    Assertions.assertThat(entity != null);
	    
	    //Test GET
	    String geturl = "http://localhost:"+port+"/api/v1/discount/4";
	    
	    Discount discountObj = this.restTemplate.getForObject(geturl, Discount.class);
	    Assertions.assertThat(discountObj.getCouponCode().equals("COM10"));
	    
	  }
	  
	  //The Above added two Book of type comic and fiction and Discount of 10 percent on comic book will be applied
	  //book Added above are of prices Comic: 500 , FICTION: 1000 (Adding 10% Discount on Comic/FICTION will cause the total cart value to 1350)
	  @Test
	  public void test6BookOrder() {
		
		//Test POST  
	    String posturl = "http://localhost:"+port+"/api/v1/getCartValue";
	    
	    Order orderObj=new Order();
	    
	    List<Long> bookIdList=new ArrayList<Long>();
	    bookIdList.add(1l);
	    bookIdList.add(2l);
	    orderObj.setBookIds(bookIdList);
	    orderObj.setCouponCodeUsed("COM10");
	    
	    
	    ResponseEntity<Object> entity = this.restTemplate.postForEntity(posturl, orderObj, Object.class);
	    Map<String,Double> response=(Map<String, Double>) entity.getBody();
	    
	    Assertions.assertThat(response.get("cartvalue").equals(new Double("1350.00")));
	    
	    
	  } 
	  
	  
	  //No Coupon Added scenario cartvalue remains 1500
	  @Test
	  public void test7BookOrder() {
		
		//Test POST  
	    String posturl = "http://localhost:"+port+"/api/v1/getCartValue";
	    
	    Order orderObj=new Order();
	    
	    List<Long> bookIdList=new ArrayList<Long>();
	    bookIdList.add(1l);
	    bookIdList.add(2l);
	    orderObj.setBookIds(bookIdList);
	    
	    
	    ResponseEntity<Object> entity = this.restTemplate.postForEntity(posturl, orderObj, Object.class);
	    Map<String,Double> response=(Map<String, Double>) entity.getBody();
	    
	    Assertions.assertThat(response.get("cartvalue").equals(new Double("1500.00")));
	    
	    
	  }  
	  
	  
	  
	  
	  
}
