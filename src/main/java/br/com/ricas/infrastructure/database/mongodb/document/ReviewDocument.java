package br.com.ricas.infrastructure.database.mongodb.document;

import br.com.ricas.domain.model.Review;
import br.com.ricas.domain.model.User;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("reviews")
public class ReviewDocument {

    private final String bookId;
    private final Date date;
    private final User user;
    private final String message;
    private final int stars;

    public ReviewDocument(String bookId, Date date, User user, String message, int stars) {
        this.bookId = bookId;
        this.date = date;
        this.user = user;
        this.message = message;
        this.stars = stars;
    }

    public Review toReview() {
        return new Review(bookId, date, user, message, stars);
    }
}
