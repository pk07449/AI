package com.pankaj.llm.embeding;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IngestionService {

    // Spring Boot auto-configures this using your application.properties settings
    private final VectorStore vectorStore;

    public IngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /**
     * Chunks text data, assigns security metadata, generates embeddings via Ollama,
     * and persists the vector output safely to PostgreSQL (pgvector).
     * @param rawText
     */
    public void ingestFlatData(String rawText) {
        // 1. Define your raw application payload data

        // 2. Wrap into a Spring AI Document object and attach metadata properties
        Document rawDocument = new Document(
                rawText,
            Map.of("owner", "user123", "city", "pune")
        );

        // 3. Initialize the Document Transformer (TokenTextSplitter)
        // Parameters: defaultChunkSize, minChunkSize, minTokenSize, maxNumChunks, keepSeparator
                TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(100)                    // Target size in tokens
                .withMinChunkSizeChars(20)             // Minimum character size constraint
                .withMinChunkLengthToEmbed(5)          // Drop chunks shorter than this token size
                .withMaxNumChunks(10)                  // Safety cap on max chunks generated
                .withKeepSeparator(true)               // Keeps punctuation separators intact
                .build();

        // 4. Split the single source document into manageable, token-safe chunks
        List<Document> chunks = splitter.apply(List.of(rawDocument));

        // 5. Load chunks directly into the pgvector database
        // Under the hood, this triggers your Ollama embedding model (e.g. nomic-embed-text)
        vectorStore.accept(chunks);

        System.out.println("Pipeline Step Complete: Data is chunked, vectorized, and saved successfully.");
    }
}
