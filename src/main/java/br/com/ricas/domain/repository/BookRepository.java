package br.com.ricas.domain.repository;

import br.com.ricas.domain.model.Book;

import java.util.List;

public interface BookRepository {
    Book save(Book book);
    List<Book> findAll();
}
