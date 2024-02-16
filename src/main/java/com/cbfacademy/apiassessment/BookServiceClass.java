package com.cbfacademy.apiassessment;

import java.util.List;
import java.util.UUID;


import org.springframework.stereotype.Service;

@Service
public class BookServiceClass implements BookService {
    BookRepository bookRepository;
   // private final List<Book> books ;

    public BookServiceClass(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        
    }

    

    /**
     * @return
     */
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Book getBook(UUID id) {
        Book book = bookRepository.find(id);
        if (book != null) {
            bookRepository.update(book);
        } else {
            System.out.println("Book with UUID " + id + " not found.");
        }
        return book;
    }

    /**
     * @param book
     * @return
     */
    @Override
    public Book createBook(Book book) {
        return bookRepository.create(book);
    }

    /**
     * @param id
     * @param updatedBook
     * @return
     */
    @Override
    public Book updateBook(UUID id, Book updatedBook) {
        Book currentBook = bookRepository.find(id);

        if (currentBook != null) {

            currentBook.setTitle(updatedBook.getTitle());
            currentBook.setAuthor(updatedBook.getAuthor());
            currentBook.setSubject(updatedBook.getSubject());
            currentBook.setLanguage(updatedBook.getLanguage());
            currentBook.setYear_published(updatedBook.getYear_published());
            currentBook.setType(updatedBook.getType());

            bookRepository.update(currentBook);
        } else {
            System.out.println("Book with UUID " + id + " not found.");
        }

        return currentBook;
    }

    /**
     * @param id
     */
    @Override
    public void deleteBook(UUID id) {
        Book bookToDelete = bookRepository.find(id);
        if (bookToDelete != null) {
            bookRepository.delete(bookToDelete);
            
        } else {
            System.out.println("Book with UUID " + id + " not found.");
        }
    }

    /**
     * @param title
     * @return
     */
    @Override
    public List<Book> findByTitle(String title) {
        List<Book> bookToFind = bookRepository.findByTitle(title);

        if (bookToFind.isEmpty()) {
            System.out.println("No books found with title: " + title);
        }

        return bookToFind;
    }

    /**
     * @param type
     * @return
     */
    @Override
    public List<Book> findByType(String type) {
        List<Book> bookToFind = bookRepository.findByType(type);

        if (bookToFind.isEmpty()) {
            System.out.println("No books found with Type: " + type);
        }

        return bookToFind;
    }

    /**
     * @param author
     * @return
     */
    @Override
    public List<Book> findByAuthor(String author) {

        List<Book> bookToFind = bookRepository.findByAuthor(author);

        if (bookToFind.isEmpty()) {
            System.out.println("No books found with author: " + author);
        }

        return bookToFind;
    }

}