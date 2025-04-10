package br.com.ricas.infrastructure.database.mongodb.document;

import br.com.ricas.domain.model.Book;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("books")
public class BookDocument {

    @Id
    String id;
    String title;
    String author;

    public BookDocument(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book toBook() {
        return new Book(title, author);
    }
}
