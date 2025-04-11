package br.com.ricas.application.web.controller;

import br.com.ricas.application.web.request.BookRequest;
import br.com.ricas.application.web.response.BookResponse;
import br.com.ricas.domain.model.Book;
import br.com.ricas.domain.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger LOGGER = Logger.getLogger(BookController.class.getName());
    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> find() {
        LOGGER.info("Finding all books");
        List<Book> list = bookService.findAll();

        List<BookResponse> response = list.stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> atlasSearch(@RequestParam String term) {
        List<Book> list = bookService.performAtlasSearchOptionC(term);
        List<BookResponse> response = list.stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody BookRequest bookRequest) {
        LOGGER.info("Creating a new book" + bookRequest);
        Book save = bookService.save(
                new Book(
                        null,
                        bookRequest.title(),
                        bookRequest.author(),
                        bookRequest.pages(),
                        bookRequest.year(),
                        bookRequest.genres(),
                        bookRequest.synopsis(),
                        bookRequest.cover(),
                        bookRequest.publishedAt()
                )
        );
        return ResponseEntity.ok(new BookResponse(save));
    }

    @PostMapping("/groupByAuthorAndExport")
    public ResponseEntity<String> groupBooksByAuthorAndExport() {
        bookService.groupBooksByAuthorAndExport();
        return ResponseEntity.ok("Function has been called successfully");
    }

    @PostMapping("/exportBooksWithPublishedYear")
    public ResponseEntity<String> exportBooksWithPublishedYear() {
        bookService.exportBooksWithPublishedYear();
        return ResponseEntity.ok("Function has been called successfully");
    }
}