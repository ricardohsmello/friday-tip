package br.com.ricas.infrastructure.database.mongodb.repository.impl;

import br.com.ricas.domain.model.Review;
import br.com.ricas.domain.repository.ReviewRepository;

import br.com.ricas.infrastructure.database.mongodb.document.BookDocument;
import br.com.ricas.infrastructure.database.mongodb.document.ReviewDocument;
import br.com.ricas.infrastructure.database.mongodb.repository.ReviewMongoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private static final Logger LOGGER = Logger.getLogger(ReviewRepositoryImpl.class.getName());

    private final ReviewMongoRepository reviewMongoRepository;

    ReviewRepositoryImpl(ReviewMongoRepository reviewMongoRepository) {
        this.reviewMongoRepository = reviewMongoRepository;
    }

    @Override
    public Review save(Review review) {
        LOGGER.info("Saving review: " + review);

        return reviewMongoRepository.save(review.toDocument()).toReview();
    }

    @Override
    public List<Review> findAll(Pageable pageable) {
        return reviewMongoRepository.findAll(pageable).getContent().stream().map(ReviewDocument::toReview).toList();
    }
}
