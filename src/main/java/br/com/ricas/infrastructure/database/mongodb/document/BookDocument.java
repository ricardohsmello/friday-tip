package br.com.ricas.infrastructure.database.mongodb.document;

import br.com.ricas.domain.model.Book;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("books")
public class BookDocument {
    @Id
    String id;
    String title;
    String author;
    int pages;
    int year;
    List<String> genres;
    String synopsis;
    String cover;
    Date publishedAt;

    public BookDocument(
            String id,
            String title,
            String author,
            int pages,
            int year,
            List<String> genres,
            String synopsis,
            String cover,
            Date publishedAt
    ) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.year = year;
        this.genres = genres;
        this.synopsis = synopsis;
        this.cover = cover;
        this.publishedAt = publishedAt;
    }

    public Book toBook() {
        return new Book(id, title, author, pages, year, genres, synopsis, cover, publishedAt);
    }
}

