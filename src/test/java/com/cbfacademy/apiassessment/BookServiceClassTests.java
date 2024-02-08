package com.cbfacademy.apiassessment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import java.util.List;

import java.util.UUID;

import org.assertj.core.api.Assertions;

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
        Book mockBook = new Book("Narrative of the Life of Frederick Douglass",
                "Frederick Douglass",
                "History",
                "English",
                1845,
                "PDF");

        when(bookRepository.find(id)).thenReturn(mockBook);

        Book result = bookService.getBook(id);

        Assertions.assertThat(result).isEqualTo(mockBook);
        verify(bookRepository, times(1)).update(mockBook);
    }

    @Test
    void testCreateBook() {
        Book newBook = new Book("Narrative of the Life of Frederick Douglass",
                "Frederick Douglass",
                "History",
                "English",
                1845,
                "PDF");

        when(bookRepository.create(newBook)).thenReturn(newBook);

        Book result = bookService.createBook(newBook);

        Assertions.assertThat(result).isEqualTo(newBook);
    }

    @Test
    void testDeleteBook() {
        UUID id = UUID.randomUUID();
        Book mockBook = new Book("Narrative of the Life of Frederick Douglass", "Frederick Douglass", "History",
                "English", 1845, "PDF");

        when(bookRepository.find(id)).thenReturn(mockBook);

        bookService.deleteBook(id);

        verify(bookRepository, times(1)).delete(mockBook);
    }

    @Test
    void testFindByAuthor() {

        List<Book> listOfBooks = new ArrayList<>();
        Book book = new Book("Narrative of the Life of Frederick Douglass",
                "Frederick Douglass",
                "History",
                "English",
                1845,
                "PDF");
        listOfBooks.add(book);

        // Mocking the behavior of bookRepository.findByAuthor()
        when(bookRepository.findByAuthor("Frederick Douglass")).thenReturn(listOfBooks);

        // Calling the method under test
        List<Book> result = bookService.findByAuthor("Frederick Douglass");

        // Asserting that the result matches the expected list of books
        assertEquals(listOfBooks, result);

    }

    @Test
    void testFindByTitle() {

        List<Book> listOfBooks = new ArrayList<>();
        Book book = new Book("Narrative of the Life of Frederick Douglass",
                "Frederick Douglass",
                "History",
                "English",
                1845,
                "PDF");
        listOfBooks.add(book);

        // Mocking the behavior of bookRepository.findByAuthor()
        when(bookRepository.findByTitle("Narrative of the Life of Frederick Douglass"))
                .thenReturn(listOfBooks);

        // Calling the method under test
        List<Book> result = bookService.findByTitle("Narrative of the Life of Frederick Douglass");

        // Asserting that the result matches the expected list of books
        assertEquals(listOfBooks, result);

    }

    @Test
    void testFindByType() {

        List<Book> listOfBooks = new ArrayList<>();
        Book book = new Book("Narrative of the Life of Frederick Douglass",
                "Frederick Douglass",
                "History",
                "English",
                1845,
                "PDF");
        listOfBooks.add(book);

        // Mocking the behavior of bookRepository.findByAuthor()
        when(bookRepository.findByType("PDF")).thenReturn(listOfBooks);

        // Calling the method under test
        List<Book> result = bookService.findByType("PDF");

        // Asserting that the result matches the expected list of books
        assertEquals(listOfBooks, result);

    }

    @Test
    void testUpdateBook() {
        UUID id = UUID.randomUUID();
        Book mockBook = new Book("Narrative of the Life of Frederick Douglass", "Frederick Douglass", "History",
                "English", 1845, "PDF");

        when(bookRepository.find(id)).thenReturn(mockBook);

        when(bookRepository.update(mockBook)).thenReturn(mockBook);

        Book result = bookService.updateBook(id, mockBook);

        Assertions.assertThat(result).isEqualTo(mockBook);

        // Verify that the bookRepository.update() method was called with the correct
        // book
        verify(bookRepository, times(1)).update(mockBook);
    }
}
