package com.pankaj.ai.chat.response;

//import com.openai.models.vectorstores.VectorStore;

import org.jspecify.annotations.Nullable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

//import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;

//import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

@RestController



@RequestMapping("/rag/ai/chatresponse/customer")
public class CustomerChatResponseController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public CustomerChatResponseController(
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
    public Customer ask(@RequestParam String question) {



        try {
            @Nullable Customer content = chatClient
                    .prompt()
                    .user(question)
                    .call()
                    .entity(Customer.class);
            return content;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}