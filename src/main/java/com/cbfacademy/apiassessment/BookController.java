package com.cbfacademy.apiassessment;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
        * Controller for handling book-related HTTP requests.
        */
@RestController
@RequestMapping("/api/books")
public class BookController {

    /** The service for performing book operations. */
    private final BookService bookService;
    /**
     * Constructs a new {@code BookController} with the specified book service.
     *
     * @param bookService The service for performing book operations.
     */
    public BookController(BookService bookService ) {
        this.bookService = bookService;
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The ResponseEntity containing the book.
     */
    @GetMapping("{id}")
    public ResponseEntity<?> getBook(@PathVariable("id") UUID id) {
        try {
            Book book = bookService.getBook(id);
            if (book != null) {
                return ResponseEntity.ok(book);
            } else {
                return ResponseEntity.notFound().build(); // Return 404 Not Found if book is not found
            }
        } catch (Exception ex) {
            ex.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while retrieving book");
        }
    }

    /**
     * Retrieves all books.
     *
     * @return The ResponseEntity containing the list of books.
     */
    @GetMapping()
    public ResponseEntity<?> getAllBooks() {
    try {

        return ResponseEntity.status(HttpStatus.FOUND).body(bookService.getAllBooks());
        } catch (Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while retrieving books") ;
    }
    }

    /**
     * Creates a new book.
     *
     * @param book The book to create.
     * @return The ResponseEntity containing the created book.
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book));
    }

    /**
     * Updates a book.
     *
     * @param id           The ID of the book to update.
     * @param bookToUpdate The updated book information.
     * @return The ResponseEntity containing the updated book.
     */
    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") UUID id, @RequestBody Book bookToUpdate) {

        return ResponseEntity.status(HttpStatus.OK).body(bookService.updateBook(id, bookToUpdate));
    }

    /**
     * Deletes a book.
     *
     * @param id The ID of the book to delete.
     * @return The ResponseEntity indicating the success of the operation.
     */
    @DeleteMapping("{id}")
    public ResponseEntity <HttpStatus> deleteBook(@PathVariable("id")UUID id) {

        try {
            
            bookService.deleteBook(id);
            return new  ResponseEntity<>(HttpStatus.NO_CONTENT);

            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }

        @GetMapping("{title}")
        public ResponseEntity<List<Book>> getBookbyTitle(@PathVariable("title") String title) {
            List<Book> bookList = bookService.findByTitle(title);
            if (bookList.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(bookList);
            }
        }


}
