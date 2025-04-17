package br.com.ricas.application.web.request;

import br.com.ricas.domain.model.User;

import java.util.Date;

public record ReviewRequest(
        String bookId,
        Date date,
        User user,
        String message,
        int stars
) {
}
