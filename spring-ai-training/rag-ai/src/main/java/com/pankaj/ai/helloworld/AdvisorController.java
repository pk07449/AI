package com.pankaj.ai.helloworld;

//import com.openai.models.vectorstores.VectorStore;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

@RestController
//@RequestMapping("/rag/ai/advisor")
public class AdvisorController {

    private final ChatClient chatClient;

    public AdvisorController(
            ChatClient.Builder builder,
            VectorStore vectorStore) {

        QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore).build();
        this.chatClient = builder
                .defaultAdvisors(

                )
                .build();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/ask")
    public String ask(@RequestParam String question) {

        String content = chatClient
                .prompt()
                .user(question)
                .call()
                .content();
        return content;
    }
}