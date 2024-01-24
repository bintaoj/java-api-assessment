package com.cbfacademy.apiassessment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookServiceClass implements BookService{
     BookRepository<Book, UUID> bookRepository;
    private final List<Book> books;

    public BookServiceClass(BookRepository<Book, UUID> bookRepository, List<Book> books) {
        this.bookRepository = bookRepository;
        this.books = books;
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
            //bookRepository.update(currentBook);
        } else {
            System.out.println("Book with UUID " + id + " not found.");
        }
        return bookRepository.update(currentBook);
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
            System.out.println("IOU with UUID " + id + " not found.");
        }
    }



    /**
     * @param title
     * @return
     */
    @Override
    public List<Book> findByTitle(String title) {
        List<Book> listOfBook = new ArrayList<>();

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                listOfBook.add(book);
            }
        }

        if (listOfBook.isEmpty()) {
            System.out.println("No books found with title: " + title);
        }

        return listOfBook;
    }


    /**
     * @param type
     * @return
     */
    @Override
    public List<Book> findByType(String type) {
        List<Book> listOfBook = new ArrayList<>();

        for (Book book : books) {
            if (book.getType().equalsIgnoreCase(type)) {
                listOfBook.add(book);
            }
        }

        if (listOfBook.isEmpty()) {
            System.out.println("No books found with type: " + type);
        }

        return listOfBook;
        }



    /**
     * @param author
     * @return
     */
    @Override
    public List<Book> findByAuthor(String author) {
        List<Book> listOfBook = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                listOfBook.add(book);
            }
        }

        if (listOfBook.isEmpty()) {
            System.out.println("No books found with title: " + author);
        }

        return listOfBook;
    }
}
