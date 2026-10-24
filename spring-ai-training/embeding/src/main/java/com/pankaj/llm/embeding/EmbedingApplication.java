package com.pankaj.llm.embeding;

import io.modelcontextprotocol.client.McpClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClientBuilderCustomizer;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
public class EmbedingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmbedingApplication.class, args);
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



