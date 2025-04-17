package br.com.ricas.domain.repository;

import br.com.ricas.domain.model.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository {

    Review save(Review review);
    List<Review> findAll(Pageable pageable);
}
