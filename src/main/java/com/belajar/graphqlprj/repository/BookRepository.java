package com.belajar.graphqlprj.repository;

import com.belajar.graphqlprj.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}