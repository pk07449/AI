package com.pankaj.ai.config;

import org.jspecify.annotations.NonNull;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommonConfig {

    @Bean
    public CommandLineRunner loadDocuments(VectorStore vectorStore) {
        return args -> {


            Document firstDoc = buildDocument("""
                    Nitish Kumar is the Chief Minister of Bihar.
                    He has served multiple terms.
                    """);

            Document firstDoc2 = buildDocument("""
                    Target for July was 500 units.""");
            Document firstDoc3 = buildDocument("""
                    Final July closing numbers showed 512 units shipped.""");
            Document firstDoc4 = buildDocument("""
                    August targets are projected to rise by 5%.""");
            vectorStore.add(List.of(firstDoc, firstDoc2, firstDoc3, firstDoc4));

            //question : Did we hit our sales target last month?
        };
    }

    private static @NonNull Document buildDocument(String content) {
        Document doc = new Document(content);
        return doc;
    }
}
