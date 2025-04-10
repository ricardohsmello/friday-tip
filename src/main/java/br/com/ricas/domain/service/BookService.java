package br.com.ricas.domain.service;

import br.com.ricas.domain.model.Book;
import br.com.ricas.domain.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    public static final int DEFAULT_PAGE_NUMBER = 0;

    public static final int DEFAULT_PAGE_SIZE = 10;

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

    public List<Book> fullTextSearch(String therm) {
        return bookRepository.fullTextSearch(therm);
    }

    public void groupBooksByAuthorAndExport() {
        bookRepository.groupBooksByAuthorAndExport();
    }
}
