package com.cbfacademy.apiassessment;

import java.io.Serializable;
import java.util.List;

import com.cbfacademy.apiassessment.core.PersistenceException;

public interface BookRepository<Book, UUID extends Serializable> {

    List<Book> findAll() throws PersistenceException;

    Book find(UUID id) throws IllegalArgumentException, PersistenceException;

    Book create(Book entity) throws IllegalArgumentException, PersistenceException;

    void delete(Book entity) throws IllegalArgumentException, PersistenceException;

    Book update(Book entity) throws IllegalArgumentException, PersistenceException;

    List<Book> findByTitle(String title) throws IllegalArgumentException, PersistenceException;

    List<Book> findByType(String type) throws IllegalArgumentException, PersistenceException;

    List<Book> findByAuthor(String author) throws IllegalArgumentException, PersistenceException;

}
