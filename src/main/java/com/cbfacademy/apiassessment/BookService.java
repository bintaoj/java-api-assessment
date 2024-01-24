package com.cbfacademy.apiassessment;

import java.util.List;
import java.util.UUID;

public interface BookService {

    List<Book> getAllBooks();

    Book getBook(UUID id);

    Book createBook(Book book);

    Book updateBook(UUID id, Book updatedBook);

    void deleteBook(UUID id);

    List<Book> findByTitle(String title);

    List<Book> findByType(String type);

    List<Book> findByAuthor(String author);
    
}
