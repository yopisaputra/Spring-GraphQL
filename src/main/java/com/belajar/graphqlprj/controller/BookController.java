package com.belajar.graphqlprj.controller;

import com.belajar.graphqlprj.model.Author;
import com.belajar.graphqlprj.model.Book;
import com.belajar.graphqlprj.model.Publisher;
import com.belajar.graphqlprj.repository.AuthorRepository;
import com.belajar.graphqlprj.repository.BookRepository;
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
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @QueryMapping
    public List<Book> books() {
        return bookRepository.findAll();
    }

    @QueryMapping
    public Optional<Book> bookById(@Argument Integer id) {
        return bookRepository.findById(id);
    }

    @QueryMapping
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @SchemaMapping
    public Optional<Author> author(Book book) {
        return authorRepository.findById(book.getAuthorId());
    }

    @QueryMapping
    public List<Publisher> publishers() {
        return publisherRepository.findAll();
    }

    @SchemaMapping
    public Optional<Publisher> publisher(Book book) {
        return publisherRepository.findById(book.getAuthorId());
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