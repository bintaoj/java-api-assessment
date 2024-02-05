package com.cbfacademy.apiassessment;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BookServiceClassTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceClass bookService;

    private List<Book> books;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        books = new ArrayList<>();
        bookService = new BookServiceClass(bookRepository, books);
    }

    @Test
    void testGetAllBooks() {
        
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        Assertions.assertThat(result).isEqualTo(books);
    }

    @Test
    void testGetBook() {
        UUID id = UUID.randomUUID();
        Book mockBook = new Book("Narrative of the Life of Frederick Douglass", "Frederick Douglass", "History", "English", 1845, "PDF");

       
        when(bookRepository.find(id)).thenReturn(mockBook);

        Book result = bookService.getBook(id);

        Assertions.assertThat(result).isEqualTo(mockBook);
        verify(bookRepository, times(1)).update(mockBook);
    }

   
    @Test
    void testCreateBook() {
        Book newBook = new Book("Narrative of the Life of Frederick Douglass", "Frederick Douglass", "History", "English", 1845, "PDF");

   
        when(bookRepository.create(newBook)).thenReturn(newBook);

        Book result = bookService.createBook(newBook);

        Assertions.assertThat(result).isEqualTo(newBook);
    }
   @Test
void testDeleteBook() {
    UUID id = UUID.randomUUID();
    Book mockBook = new Book("Narrative of the Life of Frederick Douglass", "Frederick Douglass", "History", "English", 1845, "PDF");

    when(bookRepository.find(id)).thenReturn(mockBook);

    bookService.deleteBook(id);
    
    verify(bookRepository, times(1)).delete(mockBook);
}
    
     

    @Test
    void testFindByAuthor() {

    }

    @Test
    void testFindByTitle() {

    }

    @Test
    void testFindByType() {

    }


    @Test
    void testUpdateBook() {

    }
}
