package br.com.ricas.application.web.controller;

import br.com.ricas.application.web.request.BookRequest;
import br.com.ricas.application.web.request.ReviewRequest;
import br.com.ricas.application.web.response.BookResponse;
import br.com.ricas.application.web.response.ReviewResponse;
import br.com.ricas.domain.model.Book;
import br.com.ricas.domain.model.Review;
import br.com.ricas.domain.service.BookService;
import br.com.ricas.domain.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private static final Logger LOGGER = Logger.getLogger(ReviewController.class.getName());
    private final ReviewService reviewService;

    ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> find() {
        LOGGER.info("Finding all reviews");
        List<Review> list = reviewService.findAll();

        List<ReviewResponse> response = list.stream()
                .map(ReviewResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> create(@RequestBody ReviewRequest reviewRequest) {
        LOGGER.info("Creating a new review" + reviewRequest);
        Review save = reviewService.save(
                new Review(
                        reviewRequest.bookId(),
                        reviewRequest.date(),
                        reviewRequest.user(),
                        reviewRequest.message(),
                        reviewRequest.stars()
                )
        );
        return ResponseEntity.ok(new ReviewResponse(save));
    }
}
