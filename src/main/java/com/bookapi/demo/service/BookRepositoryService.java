package com.bookapi.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookapi.demo.model.Book;


@Repository
public interface BookRepositoryService extends JpaRepository<Book, Long>{
	
    @Query("SELECT b FROM Book b WHERE b.name LIKE %?1%"
            + " OR b.isbn LIKE %?1%"
            + " OR b.type LIKE %?1%"
            + " OR b.author LIKE %?1%"
            + " OR b.description LIKE %?1%")
    public List<Book> searchFull(String keyword);
    
    Iterable<Book> findByIdIn(Collection<Long> ids);

}
