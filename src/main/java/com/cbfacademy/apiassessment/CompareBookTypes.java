package com.cbfacademy.apiassessment;

import java.util.ArrayList;
import java.util.List;

public class CompareBookTypes {

    private static JsonBookRepository bookRepository;

    public CompareBookTypes(JsonBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
 * Compares digital and hardcopy books to find books available in both formats.
 *
 * @return The list of books available in both digital and hardcopy formats.
 */
    public List<Book> compareDigitalAndHardcopyBooks() {
        List<Book> listOfBooks = bookRepository.loadDataFromJson();

        List<Book> hardcopyBooks = new ArrayList<>();
        List<Book> digitalBooks = new ArrayList<>();
        List<Book> booksInBothFormats = new ArrayList<>();

        for (Book book : listOfBooks) {
            String type = book.getType();
            if ("Digital".equals(type)) {
                digitalBooks.add(book);
            } else if ("hardcopy".equals(type)) {
                hardcopyBooks.add(book);
            }
        }
        // for (Book dBook : digitalBooks){
        // // Print or handle the lists as needed
        // System.out.println("Digital Books: " + dBook +"\n");}
        // System.out.println("---------------------------------------------------------------");
        // System.out.println("Hardcopy Books: " + hardcopyBooks);

        // Find books with the same title available in both formats
        for (Book dBook : digitalBooks) {
            for (Book hBook : hardcopyBooks) {
                if (dBook.getTitle().equalsIgnoreCase(hBook.getTitle())) {
                    booksInBothFormats.add(dBook);

                    break; // Once found, no need to continue searching for this digital book
                }
            }}

            System.out.println("\t Books available in both digital and hardcopy formats:\n");
            for (Book book : booksInBothFormats) {
                System.out.println("    *" + book.getTitle());

            }
        
        return booksInBothFormats;
    }

    public static void main(String[] args) {

        String filePath = "src\\main\\resources\\data.json";

        // Create an instance of JsonBookRepository with the provided file path
        JsonBookRepository bookRepository = new JsonBookRepository(filePath);

        // Create an instance of CompareBookTypes with the initialized book repository
        CompareBookTypes compareBookTypes = new CompareBookTypes(bookRepository);

        // Call the method to compare digital and hardcopy books
        compareBookTypes.compareDigitalAndHardcopyBooks();
    }

}
