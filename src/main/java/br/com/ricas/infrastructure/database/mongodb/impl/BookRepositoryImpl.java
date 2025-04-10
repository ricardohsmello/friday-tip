package br.com.ricas.infrastructure.database.mongodb.impl;

import br.com.ricas.domain.model.Book;
import br.com.ricas.domain.repository.BookRepository;
import br.com.ricas.infrastructure.database.mongodb.document.BookDocument;
import br.com.ricas.infrastructure.database.mongodb.BookMongoRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static br.com.ricas.domain.model.Book.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Repository
public class BookRepositoryImpl implements BookRepository {


    @Value("${spring.atlas.search_index}")
    private String BOOK_SEARCH_INDEX;

    private static final Logger LOGGER = Logger.getLogger(BookRepositoryImpl.class.getName());
    private final BookMongoRepository bookMongoRepository;
    private final MongoTemplate mongoTemplate;

    BookRepositoryImpl(
            BookMongoRepository bookMongoRepository,
            MongoTemplate mongoTemplate
    ) {
        this.bookMongoRepository = bookMongoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Book save(Book book) {
        LOGGER.info("Saving book: " + book);
        return bookMongoRepository.save(book.toDocument()).toBook();
    }

    @Override
    public List<Book> findAll(PageRequest request) {
        return bookMongoRepository.findAll(request).getContent().stream().map(BookDocument::toBook).toList();
    }

    @Override
    public List<Book> fullTextSearch(String term) {
        List<String> fields = Arrays.asList(BOOK_FIELD_TITLE, BOOK_FIELD_AUTHOR);
        String searchPaths = String.join("\", \"", fields);

        String searchStage = String.format("""
                    {
                      $search: {
                        index: "%s",
                        text: {
                          query: "%s",
                          path: ["%s"]
                        }
                      }
                    }
                """, BOOK_SEARCH_INDEX, term, searchPaths);

        AggregationOperation stage = Aggregation.stage(searchStage);
        Aggregation agg = newAggregation(
                stage
        );

        List<Book> mappedResult = mongoTemplate.aggregate(
                agg,
                BOOK_COLLECTION_NAME,
                Book.class
        ).getMappedResults();

        LOGGER.info("Found " + mappedResult.size() + " results for the term: " + term + ".");

        return mappedResult;
    }

    @Override
    public void groupBooksByAuthorAndExport() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("author")
                        .count()
                        .as("TotalBooks")
                        .push("title")
                        .as("titles"),
                Aggregation.merge()
                        .intoCollection("books_by_author")
                        .build());

        mongoTemplate.aggregate(aggregation, "books", Document.class);
    }
}
