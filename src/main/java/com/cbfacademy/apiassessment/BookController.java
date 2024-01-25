package com.cbfacademy.apiassessment;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private BookService bookService;
    
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") UUID id) {

        // bookService.getBook(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(bookService.getBook(id));
    }

    @GetMapping()
    public ResponseEntity<List<Book>> getAllIBooks() {

        return ResponseEntity.status(HttpStatus.FOUND).body(bookService.getAllBooks());

    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book));
    }

    @PutMapping("{id}")
    public ResponseEntity <Book> updateBook(@PathVariable("id") UUID id, Book bookToUpdate) {

        return ResponseEntity.status(HttpStatus.CREATED).body( bookService.updateBook(id, bookToUpdate));
    }

    @DeleteMapping("{id}")
    public ResponseEntity <Void> deleteIou(@PathVariable("id")UUID id) {

        bookService.deleteBook(id);

        try {
            bookService.deleteBook(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    
}
