package com.belajar.graphqlprj.repository;

import com.belajar.graphqlprj.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}