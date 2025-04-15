package br.com.ricas.domain.model;

import java.util.Date;

public record Review(
        Date date,
        User user,
        String message,
        int stars
) {
}
