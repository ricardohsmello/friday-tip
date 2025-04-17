package br.com.ricas.infrastructure.database.mongodb.repository.impl;

import br.com.ricas.domain.model.Book;
import br.com.ricas.domain.repository.BookRepository;
import br.com.ricas.infrastructure.database.mongodb.repository.BookMongoRepository;
import br.com.ricas.infrastructure.database.mongodb.document.BookDocument;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.search.SearchOperator;
import com.mongodb.client.model.search.SearchOptions;
import com.mongodb.client.model.search.SearchPath;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public List<Book> performAtlasSearchOptionA(String term) {
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
    public List<Book> performAtlasSearchOptionB(String term) {
        Document searchStage = new Document("$search",
                new Document("index", BOOK_SEARCH_INDEX)
                        .append("text", new Document("query", term)
                                .append("path", Arrays.asList(BOOK_FIELD_TITLE, BOOK_FIELD_AUTHOR)
                                )));

        MongoCollection<Book> collection = mongoTemplate
                .getDb()
                .getCollection(Book.BOOK_COLLECTION_NAME, Book.class);

        List<Book> result = new ArrayList<>();

        collection.aggregate(List.of(searchStage))
                .forEach(result::add);

        return result;
    }

    @Override
    public List<Book> performAtlasSearchOptionC(String term) {
        Bson search = Aggregates.search(
                SearchOperator.text(
                        List.of(
                                SearchPath.fieldPath(BOOK_FIELD_TITLE),
                                SearchPath.fieldPath(BOOK_FIELD_AUTHOR)
                        ),
                        List.of(term)),
                SearchOptions.searchOptions().index(BOOK_SEARCH_INDEX)
        );

        AggregateIterable<Document> results = mongoTemplate
                .getCollection(BOOK_COLLECTION_NAME)
                .aggregate(List.of(search));

        return StreamSupport.stream(results.spliterator(), false)
                .map(this::documentToBook)
                .collect(Collectors.toList());
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

    @Override
    public void exportBooksWithPublishedYear() {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("title", "publishedAt")
                        .andExpression("year(publishedAt)").as("year"),
                Aggregation.out("books_year_export")
        );

        mongoTemplate.aggregate(aggregation, Book.BOOK_COLLECTION_NAME, Document.class);


        /*        Document project = new Document("$project", new Document()
                .append("title", 1)
                .append("author", 1)
        );

        Document outStage = new Document("$out", new Document()
                .append("db", "reports")
                .append("coll", "new_collection")
        );

        MongoCollection<Document> collection = mongoTemplate
                .getDb()
                .getCollection("books");

        collection.aggregate(List.of(project, outStage))
                .toCollection();*/

    }

    private Book documentToBook(Document document) {
        return mongoTemplate.getConverter().read(Book.class, document);
    }
}