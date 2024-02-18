package com.cbfacademy.apiassessment;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Search {


    private  JsonBookRepository bookRepository;

    public Search(JsonBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


public static List<Book> binarySearchByLanguage(List<Book> books, String targetLanguage) {
    List<Book> result = new ArrayList<>();
    
    int left = 0;
    int right = books.size() - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        Book midBook = books.get(mid);
        String midLanguage = midBook.getLanguage();

        // Check if the language of the middle book matches the target
        int comparison = midLanguage.compareToIgnoreCase(targetLanguage);
        if (comparison == 0) {
            // Language matches, add this book to the result list
            result.add(midBook);
            
            // Expand to both sides to find more books with the same language
            int leftIndex = mid - 1;
            while (leftIndex >= 0 && books.get(leftIndex).getLanguage().equalsIgnoreCase(targetLanguage)) {
                result.add(books.get(leftIndex));
                leftIndex--;
            }
            
            int rightIndex = mid + 1;
            while (rightIndex < books.size() && books.get(rightIndex).getLanguage().equalsIgnoreCase(targetLanguage)) {
                result.add(books.get(rightIndex));
                rightIndex++;
            }
            
            return result;
        } else if (comparison < 0) {
            // If the language of the middle book comes before the target, search the right half
            left = mid + 1;
        } else {
            // If the language of the middle book comes after the target, search the left half
            right = mid - 1;
        }
    }

    // Language not found, return an empty list
    return result;
}
    public static void main(String[] args) {
        String filePath = "src\\main\\resources\\data.json";
    // Instantiate JsonBookRepository and provide necessary dependencies if any
    JsonBookRepository bookRepository = new JsonBookRepository(filePath);
    Search search = new Search(bookRepository);
   
    // Load the list of books from JSON using the repository
    List<Book> books = bookRepository.loadDataFromJson();
    // System.out.println(books);
    // System.out.println("-------------------------------------");

    // Sort the list of books by language
    Collections.sort(books, new Comparator<Book>() {
        @Override
        public int compare(Book book1, Book book2) {
            return book1.getLanguage().compareToIgnoreCase(book2.getLanguage());
        }
    });

    // Perform binary search by language
    String targetLanguage = "Arabic";
    List<Book> listOfTarget= binarySearchByLanguage(books, targetLanguage);
    System.out.println("Books in " + targetLanguage + " found" + listOfTarget);
    
    
}}
 