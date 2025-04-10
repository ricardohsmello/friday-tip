package br.com.ricas.infrastructure.repository.mongodb;

import br.com.ricas.infrastructure.document.BookDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMongoRepository extends MongoRepository<BookDocument, String> {
}
