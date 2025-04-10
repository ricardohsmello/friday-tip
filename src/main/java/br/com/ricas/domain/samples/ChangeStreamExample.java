package br.com.ricas.domain.samples;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.changestream.ChangeStreamDocument;
import org.bson.Document;

public class ChangeStreamExample {

    void test() {
        try (MongoClient mongoClient = MongoClients.create("<CONNECTION_STRING>")) {
            MongoCollection<Document> collection = mongoClient.getDatabase("friday-tip").getCollection("my_collection");

            // Open a change stream
            MongoCursor<ChangeStreamDocument<Document>> cursor = collection.watch().iterator();

            System.out.println("Listening for changes...");
            while (cursor.hasNext()) {
                ChangeStreamDocument<Document> change = cursor.next();
                System.out.println("Change detected: " + change);
            }
        }
    }
}
