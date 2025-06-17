package com.belajar.graphqlprj.controller;

import com.belajar.graphqlprj.model.Book;
import com.belajar.graphqlprj.model.Publisher;
import com.belajar.graphqlprj.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    @QueryMapping
    public List<Publisher> publishers() {
        return publisherRepository.findAll();
    }

    @SchemaMapping
    public Optional<Publisher> publisher(Book book) {
        return publisherRepository.findById(book.getAuthorId());
    }

    @MutationMapping
    public Publisher addPublisher(@Argument Integer id,
                                  @Argument String publisherName,
                                  @Argument String city) {
        if (publisherRepository.existsById(id)) {
            throw new RuntimeException("Publisher with id " + id + " has been registered");
        }

        Publisher publisher = new Publisher(id, publisherName, city);
        return publisherRepository.save(publisher);
    }

    @MutationMapping
    public Publisher updatePublisher(@Argument Integer id,
                                     @Argument Optional<String> publisherName,
                                     @Argument Optional<String> city) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));

        publisherName.ifPresent(publisher::setPublisherName);
        city.ifPresent(publisher::setCity);
        return publisherRepository.save(publisher);
    }

    @MutationMapping
    public Boolean deletePublisher(@Argument Integer id) {
        if (!publisherRepository.existsById(id)) {
            throw new RuntimeException("Publisher with id " + id + " not found");
        }

        publisherRepository.deleteById(id);
        return true;
    }
}