package br.com.ricas.infrastructure.database.mongodb.document;

import br.com.ricas.domain.model.Review;
import br.com.ricas.domain.model.User;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("reviews")
public class ReviewDocument {

    private Date date;
    private User user;
    private String message;
    private int stars;

    public ReviewDocument(Date date, User user, String message, int stars) {
        this.date = date;
        this.user = user;
        this.message = message;
        this.stars = stars;
    }

    public Review toReview() {
        return new Review(date, user, message, stars);
    }
}
