package br.com.ricas.infrastructure.config;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .readPreference(ReadPreference.nearest())
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();

        return MongoClients.create(settings);
    }

    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

}
