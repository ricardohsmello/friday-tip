package br.com.ricas.application.web.request;

 import br.com.ricas.domain.model.Review;

import java.util.Date;
import java.util.List;

public record BookRequest(
        String title,
        String author,
        int pages,
        int year,
        List<String> genres,
        String synopsis,
        String cover,
        Date publishedAt,
        List<Review> reviews
) {
}