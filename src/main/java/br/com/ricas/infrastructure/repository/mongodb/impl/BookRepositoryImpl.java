package br.com.ricas.infrastructure.repository.mongodb.impl;

import br.com.ricas.domain.model.Book;
import br.com.ricas.domain.repository.BookRepository;
import br.com.ricas.infrastructure.document.BookDocument;
import br.com.ricas.infrastructure.repository.mongodb.BookMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class BookRepositoryImpl implements BookRepository {

//    private static final Logger LOGGER = Logger.getLogger(BookRepositoryImpl.class.getName());

    private final BookMongoRepository bookMongoRepository;

    BookRepositoryImpl(BookMongoRepository bookMongoRepository) {
        this.bookMongoRepository = bookMongoRepository;
    }

    @Override
    public Book save(Book book) {
//        LOGGER.info("Saving book: " + book);
        return bookMongoRepository.save(book.toDocument()).toBook();
    }

    @Override
    public List<Book> findAll() {
        return bookMongoRepository.findAll().stream().map(BookDocument::toBook).toList();
    }
}
