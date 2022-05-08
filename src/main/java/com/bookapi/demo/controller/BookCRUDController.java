package com.bookapi.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.server.ResponseStatusException;

import com.bookapi.demo.exception.ResourceNotFoundException;
import com.bookapi.demo.model.Book;
import com.bookapi.demo.service.BookRepositoryService;

@RestController
@RequestMapping(value="/api/v1")
public class BookCRUDController {
	
    @Autowired
    private BookRepositoryService bookRepository;

    @GetMapping(value="/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    
    @GetMapping(value="/book/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Book> getbookById(@PathVariable(value = "id") Long bookId)
    throws ResourceNotFoundException {
        Book bookObj = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("book not found for this id :: " + bookId));
        return ResponseEntity.ok().body(bookObj);
    }

    @PostMapping(value="/book",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Book> createbook(@Valid @RequestBody Book book) throws ResourceNotFoundException {
    	
    		Book bookresponseObj=bookRepository.saveAndFlush(book);
    		return ResponseEntity.ok(bookresponseObj);
    }

    @PutMapping(value="/book/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Book> updatebook(@PathVariable(value = "id") Long bookId,
        @Valid @RequestBody Book bookRequest) throws ResourceNotFoundException {
        
    	//Check for book existence
    	bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));

    	bookRequest.setId(bookId);
    	
        final Book updatedBookDetails = bookRepository.save(bookRequest);
        return ResponseEntity.ok(updatedBookDetails);
    }

    @DeleteMapping(value="/book/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Map < String, Boolean >> deletebook(@PathVariable(value = "id") Long bookId)
    throws ResourceNotFoundException {
        Book bookObj = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));

        bookRepository.delete(bookObj);
        Map <String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping(value="/book/keyword/{keyword}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <List<Book>> getbookByKeyword(@PathVariable(value = "keyword") String keyword)
    throws ResourceNotFoundException {
        List<Book> bookObjList =  bookRepository.searchFull(keyword);
        return ResponseEntity.ok().body(bookObjList);
    }
}
