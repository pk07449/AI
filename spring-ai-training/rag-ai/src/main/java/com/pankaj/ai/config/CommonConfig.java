package com.pankaj.ai.config;

import org.jspecify.annotations.NonNull;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class CommonConfig {
    @Bean
    public CommandLineRunner loadDocuments(VectorStore vectorStore) {
        return _ -> {


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

            Document firstDoc5 = buildDocument("""
My name is Pankaj, I am 40 years old and live in Pune""");
            vectorStore.add(List.of(firstDoc, firstDoc2, firstDoc3, firstDoc4, firstDoc5,
                    buildDocument("i purchased my first flat in 11 Dec 2013, it was in hadapsar. "),
                    buildDocument("i purchased my second flat in 11 Feb 2019, i was in wagholi. "),
                    buildDocument("i purchased my third flat in 11 March 2022, i was in kharadi. "),
                    buildDocument("i purchased my fourth flat in 11 Dec 2024, i was in kharadi. "),
                    buildDocument("i purchased my fifth flat in 11 nov 2025, i was in kharadi. ")
            ));



            //question : Did we hit our sales target last month?
            //question : Extract customer information:               Pankaj is 40 years old.               He lives in Pune, Maharashtra.
            //question : ?question=Did%20we%20hit%20our%20sales%20target%20last%20month?
        };
    }

    private static @NonNull Document buildDocument(String content) {
        Document doc = new Document(content);

        // Load PDF from a resource (e.g., classpath or URL)
//        Resource pdfResource = new ClassPathResource("document.pdf");

// Create the reader
//        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource);

//        List<Document> documents = pdfReader.get();
//        return documents.get(0);
        return doc;
    }
}
