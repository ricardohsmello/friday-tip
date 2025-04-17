package br.com.ricas.domain.model;

import br.com.ricas.infrastructure.database.mongodb.document.ReviewDocument;

import java.util.Date;

public record Review(
        String bookId,
        Date date,
        User user,
        String message,
        int stars
) {

    public ReviewDocument toDocument() {
        return new ReviewDocument(bookId, date, user, message, stars);
    }
}
