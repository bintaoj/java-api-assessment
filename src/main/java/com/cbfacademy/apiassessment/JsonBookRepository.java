package com.cbfacademy.apiassessment;

import com.cbfacademy.apiassessment.core.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Repository
public class JsonBookRepository implements BookRepository<Book, UUID> {

    private final Path filePath;
    private final Gson gson;
    private final Map<UUID, Book> database;

    public JsonBookRepository(@Value("${json.file.path}") String filePath) {
        this.filePath = Paths.get(filePath);
        gson = new GsonBuilder()
        .serializeNulls()
        .create();
        database = loadDataFromJson();
    }

    private Map<UUID, Book> loadDataFromJson() {
        try {
            if (Files.exists(filePath)) {
                String jsonContent = Files.readString(filePath);
                TypeToken<Map<UUID, Book>> typeToken = new TypeToken<Map<UUID, Book>>() {};
                return gson.fromJson(jsonContent, typeToken.getType());
            } else {
                // Create the file if it doesn't exist
                Files.createFile(filePath);
                return new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }


    private void saveDataToJson() {
        try {
            // Read existing data from the file
            Map<UUID, Book> existingData = loadDataFromJson();

            // Merge existing data with the current state of the database
            existingData.putAll(database);

            // Write the combined data back to the file
            try (Writer writer = new FileWriter(String.valueOf(filePath))) {
                gson.toJson(existingData, writer);
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
        List<Book> books = new ArrayList<>(database.values());
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
        return database.get(id);
    }

    /**
     * @param entity
     * @return
     * @throws IllegalArgumentException
     * @throws PersistenceException
     */
    @Override
    public Book create(Book entity) throws IllegalArgumentException, PersistenceException {
        database.put(entity.getId(), entity);
        saveDataToJson();
        return  entity;

    }

    /**
     * @param entity
     * @throws IllegalArgumentException
     * @throws PersistenceException
     */
    @Override
    public void delete(Book entity) throws IllegalArgumentException, PersistenceException {
            database.remove(entity.getId());
            saveDataToJson();
    }

    /**
     * @param entity
     * @return
     * @throws IllegalArgumentException
     * @throws PersistenceException
     */
    @Override
    public Book update(Book entity) throws IllegalArgumentException, PersistenceException {


        return null;
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
    
}
