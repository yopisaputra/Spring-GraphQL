package com.belajar.graphqlprj.controller;

import com.belajar.graphqlprj.model.Author;
import com.belajar.graphqlprj.model.Book;
import com.belajar.graphqlprj.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @QueryMapping
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @SchemaMapping
    public Optional<Author> author(Book book) {
        return authorRepository.findById(book.getAuthorId());
    }

    @MutationMapping
    public Author addAuthor(@Argument Integer id,
                            @Argument String firstName,
                            @Argument String lastName) {

        if (authorRepository.existsById(id)) {
            throw new RuntimeException("Author with id " + id + " has been registered");
        }

        Author author = new Author(id, firstName, lastName);
        return authorRepository.save(author);
    }

    @MutationMapping
    public Author updateAuthor(@Argument Integer id,
                               @Argument Optional<String> firstName,
                               @Argument Optional<String> lastName) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        firstName.ifPresent(author::setFirstName);
        lastName.ifPresent(author::setLastName);
        return authorRepository.save(author);
    }

    @MutationMapping
    public Boolean deleteAuthor(@Argument Integer id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author with id " + id + " not found");
        }
        authorRepository.deleteById(id);
        return true;
    }

}
