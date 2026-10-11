package com.pankaj.llm.embeding;

import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderAgent {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public OrderAgent(ChatClient.Builder builder,
                      VectorStore vectorStore) {
        this.vectorStore = vectorStore;

        // 1. Define your strict contextual guardrails for the RAG step
        String strictRagTemplate = """
                You are a strict, single-purpose Order Management Assistant. 
                You are ONLY permitted to handle explicit retail or e-commerce order tasks (tracking, checking order status, placement).
                
                CRITICAL RAG CONSTRAINTS:
                - The provided context contains information regarding user transactions. 
                - However, if the user query asks about real estate, apartments, housing, or "flats" (such as "give me my all flats"), you MUST completely ignore the context and refuse to answer.
                - Do not attempt to process or answer inquiries outside of direct retail order contexts.
                
                REJECTION PHRASE:
                If the query is not explicitly about a retail order, reply EXACTLY with:
                "I can only assist you with order-related inquiries. Please ask an order-specific question."
                
                ---------------------
                CONTEXT DATA:
                {context}
                ---------------------
                
                USER QUERY: {query}
                """;

        // 2. Build the Document Retriever (Spring AI 2.0 Standard)
        VectorStoreDocumentRetriever documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
//                .similarityThreshold(0.70) // Prevents low-relevance junk from triggering answers
//                .topK(3)
                .build();

        // 3. Attach your custom Prompt Template to the Query Augmenter
        ContextualQueryAugmenter queryAugmenter = ContextualQueryAugmenter.builder()
//                .allowEmptyContext(true) // Allows standard chat flow even if database returns nothing
                .promptTemplate(new PromptTemplate(strictRagTemplate))
                .build();

        // 4. Assemble the Spring AI 2.0 Modular Advisor
        RetrievalAugmentationAdvisor strictRagAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(documentRetriever)
                .queryAugmenter(queryAugmenter)
                .build();

        // 5. Initialize the ChatClient with the default advisor
        this.chatClient = builder
                .defaultAdvisors(strictRagAdvisor)
                .build();
//
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        try {
            return chatClient
                    .prompt()
                    .user(question)
                    .call()
                    .content();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
