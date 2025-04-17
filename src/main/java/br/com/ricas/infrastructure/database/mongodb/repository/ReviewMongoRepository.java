package br.com.ricas.infrastructure.database.mongodb.repository;

import br.com.ricas.infrastructure.database.mongodb.document.ReviewDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewMongoRepository extends MongoRepository<ReviewDocument, String> {
}
