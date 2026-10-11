package com.pankaj.llm.prompting;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientBuilderCustomizer;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PromptingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PromptingApplication.class, args);
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) { // (2)
        return chatClientBuilder.build();
    }

    @Bean
    ChatClientBuilderCustomizer loggingCustomizer() {
        return builder -> builder.defaultAdvisors(
                new SimpleLoggerAdvisor()
        );
    }


}
