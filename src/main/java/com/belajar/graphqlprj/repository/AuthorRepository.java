package com.belajar.graphqlprj.repository;

import com.belajar.graphqlprj.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}