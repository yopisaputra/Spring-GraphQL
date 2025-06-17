package com.belajar.graphqlprj.controller;

import com.belajar.graphqlprj.model.Book;
import com.belajar.graphqlprj.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @QueryMapping
    public List<Book> books() {
        return bookRepository.findAll();
    }

    @QueryMapping
    public Optional<Book> bookById(@Argument Integer id) {
        return bookRepository.findById(id);
    }

    @MutationMapping
    public Book addBook(@Argument Integer id,
                        @Argument String name,
                        @Argument Integer pageCount,
                        @Argument Integer authorId,
                        @Argument Integer publisherId) {

        if (bookRepository.existsById(id)) {
            throw new RuntimeException("Book with id " + id + " has been registered");
        }

        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setPageCount(pageCount);
        book.setAuthorId(authorId);
        book.setPublisherId(publisherId);

        return bookRepository.save(book);
    }

    @MutationMapping
    public Book updateBook(@Argument Integer id,
                           @Argument Optional<String> name,
                           @Argument Optional<Integer> pageCount,
                           @Argument Optional<Integer> authorId,
                           @Argument Optional<Integer> publisherId) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        name.ifPresent(book::setName);
        pageCount.ifPresent(book::setPageCount);
        authorId.ifPresent(book::setAuthorId);
        publisherId.ifPresent(book::setPublisherId);
        return bookRepository.save(book);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book with id " + id + " not found");
        }

        bookRepository.deleteById(id);
        return true;
    }
}