package br.com.ricas;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipRepository extends MongoRepository<Tip, String> {

}
