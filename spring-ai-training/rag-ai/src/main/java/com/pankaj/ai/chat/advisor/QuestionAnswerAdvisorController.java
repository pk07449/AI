package com.pankaj.ai.chat.advisor;

//import com.openai.models.vectorstores.VectorStore;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.vectorstore.VectorStore;

//import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;

import java.util.List;

//import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

@RestController
@RequestMapping("/rag/ai/advisor")
public class QuestionAnswerAdvisorController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public QuestionAnswerAdvisorController(
            ChatClient.Builder builder,
            VectorStore vectorStore) {

        QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore).build();
        this.chatClient = builder
                .defaultAdvisors(
questionAnswerAdvisor
                )
                .build();
        this.vectorStore = vectorStore;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/ask")
    public String ask(@RequestParam String question) {


        System.out.println("QUESTION = " + question);

        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question)
                        .topK(5)
                        .build()
        );

        System.out.println("DOCUMENT COUNT = " + docs.size());

        docs.forEach(d ->
                System.out.println("DOCUMENT = " + d.getText())
        );

        try {
            String content = chatClient
                    .prompt()
                    .user(question)
                    .call()
                    .content();
            return content;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}