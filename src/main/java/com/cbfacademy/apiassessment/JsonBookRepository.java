package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.core.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A repository implementation that stores book data in a JSON file.
 */
@Repository
public class JsonBookRepository implements BookRepository {

    /** The file path where JSON data is stored. */
    private final Path filePath;

    /** Gson instance for JSON serialization/deserialization. */
    private final Gson gson;

    /** The list of books currently loaded in memory. */
    private final List<Book> books;

    /**
     * Constructs a new {@code JsonBookRepository} with the specified file path.
     *
     * @param filePath The path to the JSON file storing book data.
     */
    public JsonBookRepository(@Value("${json.file.path}") String filePath) {
        this.filePath = Paths.get(filePath);
        gson = new GsonBuilder().create();
        books = loadDataFromJson();
    }

    /**
     * Loads book data from the JSON file.
     *
     * @return The list of books loaded from the JSON file.
     */
     List<Book> loadDataFromJson() {
        try {
            if (Files.exists(filePath) && Files.size(filePath) > 0) {
                // Proceed to read and parse the JSON file
                try (Stream<String> lines = Files.lines(filePath)) {
                    List<Book> books = lines
                            .map(line -> gson.fromJson(line, Book.class))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    // System.out.println(books);
                    // System.out.println("---------------------------------------------------------------------");
                    return books;
                }
            } else {
                // Create the file if it doesn't exist or is empty
                Files.createFile(filePath);
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exception appropriately, e.g., log it
            return new ArrayList<>();
        }
    }

    /**
     * Saves the current list of books to the JSON file.
     */
    private void saveDataToJson() {
        try {
            // Read existing data from the file
            List<Book> existingData = loadDataFromJson();

            // Set to store unique identifiers (title + type)
            Set<String> existingIdentifiers = new HashSet<>();
            for (Book book : existingData) {
                existingIdentifiers.add(book.getTitle() + book.getType());
            }

            // Add the current state of the file to existing data, avoiding duplicates
            for (Book book : books) {
                String identifier = book.getTitle() + book.getType();
                if (!existingIdentifiers.contains(identifier)) {
                    existingData.add(book);
                    existingIdentifiers.add(identifier); // Update the set with the new book identifier
                }
            }

            // Write the combined data back to the file with each book on a new line
            try (Writer writer = new FileWriter(String.valueOf(filePath))) {
                for (Book book : existingData) {
                    gson.toJson(book, writer);
                    writer.write(System.lineSeparator()); // Add a newline after each book
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Need to create a custom exception to say book already exists.
        }
    }

    /**
     * Retrieves all books from the repository.
     *
     * @return An unmodifiable list containing all books in the repository.
     * @throws PersistenceException If an error occurs while retrieving the books.
     */
    @Override
    public List<Book> findAll() throws PersistenceException {

        System.out.println(books);
        return Collections.unmodifiableList(books);
    }

    /**
     * Finds a book in the repository by its unique identifier.
     *
     * @param id The unique identifier of the book to find.
     * @return The book with the specified identifier, or null if not found.
     * @throws IllegalArgumentException If the provided ID is null.
     * @throws PersistenceException     If an error occurs while searching for the
     *                                  book.
     */
    @Override
    public Book find(UUID id) throws IllegalArgumentException, PersistenceException {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    /**
     * Creates a new book in the repository.
     *
     * @param entity The book to be created.
     * @return The newly created book.
     * @throws IllegalArgumentException If the provided book entity is null.
     * @throws PersistenceException     If an error occurs while creating the book.
     */
    @Override
    public Book create(Book entity) throws IllegalArgumentException, PersistenceException {
        Book newBook = new Book(entity.getTitle(),
                entity.getAuthor(),
                entity.getSubject(),
                entity.getLanguage(),
                entity.getYear_published(),
                entity.getType());
        books.add(newBook);
        saveDataToJson();
        return newBook;

    }

    /**
     * Deletes a book from the repository.
     *
     * @param entity The book to be deleted.
     * @throws IllegalArgumentException If the provided book entity is null.
     * @throws PersistenceException     If an error occurs while deleting the book.
     */
    @Override
    public void delete(Book entity) throws IllegalArgumentException, PersistenceException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        UUID deleteBookbyUuid = entity.getId();
        for (int i = 0; i < books.size(); i++) {
            Book currentBook = books.get(i);
            if (currentBook.getId().equals(deleteBookbyUuid)) {
                books.remove(i);
                saveDataToJson();
                return;
            }
        }
        throw new PersistenceException("Book with ID " + deleteBookbyUuid + " not found for deletion.");
    }

    /**
     * Updates an existing book in the repository.
     *
     * @param entity The updated book entity.
     * @return The updated book.
     * @throws IllegalArgumentException If the provided book entity is null.
     * @throws PersistenceException     If an error occurs while updating the book.
     */
    @Override
    public Book update(Book entity) throws IllegalArgumentException, PersistenceException {

        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        UUID updateBookUuid = entity.getId();
        for (Book book : books) {
            if (book.getId().equals(updateBookUuid)) {
                book.setTitle(entity.getTitle());
                book.setAuthor(entity.getAuthor());
                book.setSubject(entity.getSubject());
                book.setLanguage(entity.getLanguage());
                book.setYear_published(entity.getYear_published());
                book.setType(entity.getType());

                saveDataToJson();
                return book;
            }
        }
        throw new PersistenceException("Book with ID " + updateBookUuid + " not found for update.");
    }

    /**
     * Finds books in the repository by their title.
     *
     * @param title The title of the books to find.
     * @return A list of books with the specified title.
     * @throws IllegalArgumentException If the provided title is null or empty.
     * @throws PersistenceException If an error occurs while searching for the books.
     *                                  
     */
    @Override
    public List<Book> findByTitle(String title) throws IllegalArgumentException, PersistenceException {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        //try {
       // Read the list of books from the JSON file
            //List<Book> allBooks = loadDataFromJson();

            // Filter books by title
            List<Book> matchingBooks = new ArrayList<>();
            for (Book book : books) {
                if (book.getTitle().equalsIgnoreCase(title)) {
                    matchingBooks.add(book);
                }
            }
            saveDataToJson();
            return matchingBooks;
        // } catch (IOException e) {
        // throw new PersistenceException("Error reading books from JSON file");
        // }
    }

    /**
     * @param type
     * @return
     * @throws IllegalArgumentException
     * @throws PersistenceException
     */
    @Override
    public List<Book> findByType(String type) throws IllegalArgumentException, PersistenceException {
        List<Book> allBooks = loadDataFromJson();

        // Filter books by title
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getType().equalsIgnoreCase(type)) {
                matchingBooks.add(book);
            }
        }
        saveDataToJson();
        return matchingBooks;
    }

    /**
     * @param author
     * @return
     * @throws IllegalArgumentException
     * @throws PersistenceException
     */
    @Override
    public List<Book> findByAuthor(String author) throws IllegalArgumentException, PersistenceException {
        List<Book> allBooks = loadDataFromJson();

        // Filter books by title
        List<Book> matchingBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                matchingBooks.add(book);
            }
        }
        saveDataToJson();
        return matchingBooks;
    }

}
