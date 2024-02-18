package com.cbfacademy.apiassessment;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
Service class for performing operations related to books.
*/
@Service
public class BookServiceClass implements BookService {
    BookRepository bookRepository;
    List<Book> books;
   
    /**
     * Constructs a new {@code BookServiceClass} with the specified book repository.
     *
     * @param bookRepository The repository for book data.
     */
    public BookServiceClass(BookRepository bookRepository, List<Book> books ) {
        this.bookRepository = bookRepository;
        this.books = books;

        
    }

    

    /**
     * Retrieves all books from the repository.
     *
     * @return A list containing all books in the repository.
     */
    @Override
    public List<Book> getAllBooks() {
        try {
            return bookRepository.findAll();
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Error while retrieving all books", ex);
        }
    }

    /**
     * Retrieves a book from the repository by its unique identifier.
     * If the book is found, it is updated in the repository.
     *
     * @param id The unique identifier of the book to retrieve.
     * @return The book with the specified identifier, or null if not found.
     */
    @Override
    public Book getBook(UUID id) {
        try {
            Book book = bookRepository.find(id);
            if (book != null) {
                bookRepository.update(book);
            }

            return book;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error while retrieving book with ID: " + id, ex);
        }
    }

    /**
     * Creates a new book in the repository.
     *
     * @param book The book to create.
     * @return The created book.
     * @throws RuntimeException If an error occurs while creating the book.
     */
    @Override
    public Book createBook(Book book) {
        try {
             return bookRepository.create(book);
    
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Error while creating book", ex);
        }
        
    }

    /**
     * Updates a book in the repository with the specified ID using the provided updated book information.
     *
     * @param id          The unique identifier of the book to update.
     * @param updatedBook The updated information for the book.
     * @return The updated book.
     * @throws RuntimeException If an error occurs while updating the book.
     */
    @Override
    public Book updateBook(UUID id, Book updatedBook) {
        try {
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
        } catch (Exception ex) {
            ex.printStackTrace(); 
            throw new RuntimeException("Error while updating book with ID: " + id, ex);
        }
    }

    /**
     * Deletes a book from the repository by its unique identifier.
     *
     * @param id The unique identifier of the book to delete.
     * @throws RuntimeException If an error occurs while deleting the book.
     */
    @Override
    public void deleteBook(UUID id) {
        try {
            Book bookToDelete = bookRepository.find(id);
            if (bookToDelete != null) {
                bookRepository.delete(bookToDelete);
            } else {
                System.out.println("Book with UUID " + id + " not found.");
            }
        } catch (Exception ex) {
          
            ex.printStackTrace(); 
            throw new RuntimeException("Error while deleting book with ID: " + id, ex);
        }
    }

    /**
     * @param title
     * @return
     */
    @Override
    public List<Book> findByTitle(String title) {
    try {  
        List<Book> bookToFind = bookRepository.findByTitle(title);

        if (bookToFind.isEmpty()) {
            System.out.println("No books found with title: " + title);
        }

        return bookToFind;
        } catch (Exception ex) {

        ex.printStackTrace();
        throw new RuntimeException("Error while getting books with Title: " + title, ex);
    }
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