package com.pankaj.llm.embeding;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurePropertySearchController {

    private final ChatClient.Builder chatClientBuilder;
    private final VectorStore vectorStore;

    public SecurePropertySearchController(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClientBuilder = chatClientBuilder;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/api/search-flats")
    public String searchFlats(
            @RequestParam(value = "query") String userQuery,
            @RequestParam(value = "userId") String userId) {

        // 1. Build a SQL-like metadata filter rule safely using Spring AI FilterExpressionBuilder
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        Filter.Expression metadataFilter = b.and(
            b.eq("owner", userId),
            b.eq("city", "pune")
        ).build();


        // 2. Build the Document Retriever (Spring AI 2.0 Standard)
        VectorStoreDocumentRetriever documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore)
                .filterExpression(metadataFilter)
//                .similarityThreshold(0.70) // Prevents low-relevance junk from triggering answers
//                .topK(3)
                .build();

        // 3. Attach your custom Prompt Template to the Query Augmenter
        ContextualQueryAugmenter queryAugmenter = ContextualQueryAugmenter.builder()
//                .allowEmptyContext(true) // Allows standard chat flow even if database returns nothing
//                .promptTemplate(new PromptTemplate(strictRagTemplate))
                .build();

        // 4. Assemble the Spring AI 2.0 Modular Advisor
        RetrievalAugmentationAdvisor strictRagAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(documentRetriever)
                .queryAugmenter(queryAugmenter)
                .build();

        // 3. Construct a client passing the customized QuestionAnswerAdvisor
        ChatClient localizedChatClient = chatClientBuilder
                .defaultAdvisors(strictRagAdvisor)
                .build();

        // 4. Request the text generation from your Ollama model
        return localizedChatClient.prompt()
                .user(userQuery)
                .call()
                .content();
    }
}
