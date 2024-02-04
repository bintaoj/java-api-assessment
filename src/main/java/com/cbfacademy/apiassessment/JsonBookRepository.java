package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.core.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
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

@Repository
public class JsonBookRepository implements BookRepository {

    private final Path filePath;
    private final Gson gson;
    private final List<Book> books;

    public JsonBookRepository(@Value("${json.file.path}") String filePath) {
        this.filePath = Paths.get(filePath);
        gson = new GsonBuilder().create();
        books = loadDataFromJson();
    }

    private List<Book> loadDataFromJson() {
        try {
            if (Files.exists(filePath) && Files.size(filePath) > 0) {
                // Proceed to read and parse the JSON file
                try (Stream<String> lines = Files.lines(filePath)) {
                    List<Book> books = lines
                            .map(line -> gson.fromJson(line, Book.class))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());

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


    private void saveDataToJson() {
        try {
            // Read existing data from the file
            List<Book> existingData = loadDataFromJson();

            // Add the current state of the database to existing data
            existingData.addAll(books);

            // Write the combined data back to the file with each book on a new line
            try (Writer writer = new FileWriter(String.valueOf(filePath))) {
                for (Book book : existingData) {
                    gson.toJson(book, writer);
                    writer.write(System.lineSeparator()); // Add a newline after each book
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Book> findAll() throws PersistenceException {
    
        //List<Book> books = new ArrayList<>();
        return Collections.unmodifiableList(books);
    }

    /**
     * @param id
     * @return
     * @throws IllegalArgumentException
     * @throws PersistenceException
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
     * @param entity
     * @return
     * @throws IllegalArgumentException
     * @throws PersistenceException
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
     * @param entity
     * @throws IllegalArgumentException
     * @throws PersistenceException
     */
    @Override
    public void delete(Book entity) throws IllegalArgumentException, PersistenceException {
           if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        UUID deleteBookUuid = entity.getId();
        for (int i = 0; i < books.size(); i++) {
            Book currentBook = books.get(i);
            if (currentBook.getId().equals(deleteBookUuid)) {
                books.remove(i);
                saveDataToJson();
            }
        }
        throw new PersistenceException("Book with ID " + deleteBookUuid + " not found for deletion.");
    }

    /**
     * @param entity
     * @return
     * @throws IllegalArgumentException
     * @throws PersistenceException
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
     * @param title
     * @return
     * @throws IllegalArgumentException
     * @throws PersistenceException
     */
    @Override
    public List<Book> findByTitle(String title) throws IllegalArgumentException, PersistenceException {
        return null;
    }

    /**
     * @param type
     * @return
     * @throws IllegalArgumentException
     * @throws PersistenceException
     */
    @Override
    public List<Book> findByType(String type) throws IllegalArgumentException, PersistenceException {
        return null;
    }

    /**
     * @param author
     * @return
     * @throws IllegalArgumentException
     * @throws PersistenceException
     */
    @Override
    public List<Book> findByAuthor(String author) throws IllegalArgumentException, PersistenceException {
        return null;
    }
   
    // public JsonBookRepository(@Value("${json.file.path}") String filePath) {
    //     this.filePath = Paths.get(filePath);
    //     gson = new GsonBuilder()
    //             // .serializeNulls()
    //             .create();
    //     database = loadDataFromJson();
    // }

    // private Map<UUID, Book> loadDataFromJson() {
    //     try {
    //         if (Files.exists(filePath)) {
    //             String jsonContent = Files.readString(filePath);
    //             TypeToken<Map<UUID, Book>> typeToken = new TypeToken<Map<UUID, Book>>() {
    //             };
    //             return gson.fromJson(jsonContent, typeToken.getType());
    //         } else {
    //             // Create the file if it doesn't exist
    //             Files.createFile(filePath);
    //             return new HashMap<>();
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return new HashMap<>();
    // }

    // private void saveDataToJson() {
    //     try {
    //         // Read existing data from the file
    //         Map<UUID, Book> existingData = loadDataFromJson();

    //         // Merge existing data with the current state of the database
    //         existingData.putAll(database);

    //         // Write the combined data back to the file
    //         try (Writer writer = new FileWriter(String.valueOf(filePath))) {
    //             gson.toJson(existingData, writer);
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // } 
}
