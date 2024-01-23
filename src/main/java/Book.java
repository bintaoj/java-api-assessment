
import java.util.UUID;

public class Book {

    private final UUID id;
    private String title;
    private String author;
    private String subject;
    private String language;
    private int year_published;
    private String type;

    public Book(String title, String author, String subject, String language, int year_published, String type) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.language = language;
        this.year_published = year_published;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getYear_published() {
        return year_published;
    }

    public void setYear_published(int year_published) {
        this.year_published = year_published;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", subject='" + subject + '\'' +
                ", language='" + language + '\'' +
                ", year_published=" + year_published +
                ", type='" + type + '\'' +
                '}';
    
}


}
