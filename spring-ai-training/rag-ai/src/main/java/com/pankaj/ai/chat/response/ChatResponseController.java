package com.pankaj.ai.chat.response;

//import com.openai.models.vectorstores.VectorStore;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;

//import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

@RestController



@RequestMapping("/rag/ai/chatresponse")
public class ChatResponseController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public ChatResponseController(
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
    public CustomChatResponse ask(@RequestParam String question) {



        try {
            @Nullable CustomChatResponse content = chatClient
                    .prompt()
                    .user(question)
                    .call()
                    .entity(CustomChatResponse.class);
            return content;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}