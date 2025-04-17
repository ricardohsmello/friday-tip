package br.com.ricas.domain.service;

import br.com.ricas.domain.model.Book;
import br.com.ricas.domain.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.ricas.domain.util.Constants.DEFAULT_PAGE_NUMBER;
import static br.com.ricas.domain.util.Constants.DEFAULT_PAGE_SIZE;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        PageRequest request = PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, Sort.unsorted());

        return bookRepository.findAll(request);
    }

    public List<Book> performAtlasSearchOptionA(String therm) {
        return bookRepository.performAtlasSearchOptionA(therm);
    }

    public List<Book> performAtlasSearchOptionB(String therm) {
        return bookRepository.performAtlasSearchOptionB(therm);
    }

    public List<Book> performAtlasSearchOptionC(String therm) {
        return bookRepository.performAtlasSearchOptionC(therm);
    }

    public void groupBooksByAuthorAndExport() {
        bookRepository.groupBooksByAuthorAndExport();
    }

    public void exportBooksWithPublishedYear() {
        bookRepository.exportBooksWithPublishedYear();
    }
}
