package br.com.ricas.domain.model;

import br.com.ricas.infrastructure.document.BookDocument;

public record Book(String title, String author) {

    public BookDocument toDocument() {
        return new BookDocument(null, this.title, this.author);
    }
}
