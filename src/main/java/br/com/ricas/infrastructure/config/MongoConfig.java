package br.com.ricas.infrastructure.config;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collections;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)

public class MongoConfig {
    /*
    @Value("${spring.data.mongodb.uri}")
    private String connectionString;

    @Value("${spring.data.mongodb.database}")
    private String database;


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
    */

  @Bean
  public MongoTemplate mongoTemplate(
          MongoDatabaseFactory databaseFactory,
          MappingMongoConverter converter
  ) {
    converter.setTypeMapper(new DefaultMongoTypeMapper(null));
    return new MongoTemplate(databaseFactory, converter);
  }

}
