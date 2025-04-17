package br.com.ricas.domain.service;

import br.com.ricas.domain.model.Book;
import br.com.ricas.domain.model.Review;
import br.com.ricas.domain.repository.ReviewRepository;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.List;

import static br.com.ricas.domain.util.Constants.DEFAULT_PAGE_NUMBER;
import static br.com.ricas.domain.util.Constants.DEFAULT_PAGE_SIZE;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MongoTemplate mongoTemplate;

    public ReviewService(ReviewRepository reviewRepository, MongoTemplate mongoTemplate) {
        this.reviewRepository = reviewRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Review> findAll() {
        PageRequest request = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.unsorted());
        return reviewRepository.findAll(request);
    }

    public Review save(Review review) {
        Review saved = reviewRepository.save(review);

        mongoTemplate.update(Book.class)
                .matching(where("id").is(review.bookId()))
                .apply(new Update().push("reviews", new Document("$each", List.of(review))
                        .append("$sort", new Document("timestamp", -1))
                        .append("$slice", 5)))
                .first();

        return saved;
    }
}
