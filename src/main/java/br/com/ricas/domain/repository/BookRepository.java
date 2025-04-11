package br.com.ricas.domain.repository;

import br.com.ricas.domain.model.Book;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface BookRepository {
    Book save(Book book);
    List<Book> findAll(PageRequest request);
    List<Book> performAtlasSearchOptionA(String term);
    List<Book> performAtlasSearchOptionB(String term);
    List<Book> performAtlasSearchOptionC(String term);
    void groupBooksByAuthorAndExport();
    void exportBooksWithPublishedYear();
}
