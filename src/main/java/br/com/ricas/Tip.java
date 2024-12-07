package br.com.ricas;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tip")
public class Tip {

    @Id
    private String id;
    private String description;
    private String language;
    private LocalDateTime createdAt;
}
