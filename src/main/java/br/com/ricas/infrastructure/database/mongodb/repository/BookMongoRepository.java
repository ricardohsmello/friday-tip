package br.com.ricas.infrastructure.database.mongodb.repository;

import br.com.ricas.infrastructure.database.mongodb.document.BookDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMongoRepository extends MongoRepository<BookDocument, String> {
}
