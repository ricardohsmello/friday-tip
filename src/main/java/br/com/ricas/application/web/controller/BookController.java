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
                .map(book -> new BookResponse(book.title(), book.author()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody BookRequest bookRequest) {
        LOGGER.info("Creating a new book" + bookRequest);
        Book save = bookService.save(new Book(bookRequest.title(), bookRequest.author()));
        return ResponseEntity.ok(new BookResponse(save.title(), save.author()));
    }
}


