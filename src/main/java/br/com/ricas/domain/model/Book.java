package br.com.ricas.domain.model;

import br.com.ricas.infrastructure.database.mongodb.document.BookDocument;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public record Book(
        String id,
        String title,
        String author,
        int pages,
        int year,
        List<String> genres,
        String synopsis,
        String cover,
        Date publishedAt,
        List<Review> reviews) {

    public static String BOOK_FIELD_TITLE = "title";
    public static String BOOK_FIELD_AUTHOR = "author";
    public static String BOOK_COLLECTION_NAME = "books";

    public BookDocument toDocument() {
        return new BookDocument(null, this.title, this.author, this.pages, this.year, this.genres, this.synopsis, this.cover, this.publishedAt, Optional.ofNullable(reviews)
                .map(list -> list.stream().map(Review::toDocument).toList())
                .orElse(Collections.emptyList()));
    }
}
