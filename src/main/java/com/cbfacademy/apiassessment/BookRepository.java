package com.cbfacademy.apiassessment;


import java.util.List;
import java.util.UUID;

import com.cbfacademy.apiassessment.core.PersistenceException;
import com.cbfacademy.apiassessment.core.Repository;

public interface BookRepository extends Repository<Book, UUID> {

   
    List<Book> findByTitle(String title) throws IllegalArgumentException, PersistenceException;

    List<Book> findByType(String type) throws IllegalArgumentException, PersistenceException;

    List<Book> findByAuthor(String author) throws IllegalArgumentException, PersistenceException;

}
