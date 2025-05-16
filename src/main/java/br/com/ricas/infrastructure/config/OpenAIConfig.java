package br.com.ricas.infrastructure.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig  {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Bean
    public EmbeddingModel embeddingModel() {
        return new OpenAiEmbeddingModel(OpenAiApi.builder().apiKey(apiKey).build());
    }

    /*
    * @Bean
public VectorStore vectorStore(MongoTemplate mongoTemplate, EmbeddingModel embeddingModel) {
    return MongoDBAtlasVectorStore.builder(mongoTemplate, embeddingModel)
        .collectionName("custom_vector_store")           // Optional: defaults to "vector_store"
        .vectorIndexName("custom_vector_index")          // Optional: defaults to "vector_index"
        .pathName("custom_embedding")                    // Optional: defaults to "embedding"
        .numCandidates(500)                             // Optional: defaults to 200
        .metadataFieldsToFilter(List.of("author", "year")) // Optional: defaults to empty list
        .initializeSchema(true)                         // Optional: defaults to false
        .batchingStrategy(new TokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
        .build();
}
    * */
}
