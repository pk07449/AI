package com.pankaj.spring.ai.springaimcpclient;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.Random;

@SpringBootApplication
public class SpringAiMcpClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiMcpClientApplication.class, args);
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) { // (2)
        return chatClientBuilder.build();
    }

    String userPrompt = """
		Check the weather in Pune right now and show the current time!
		Please incorporate all creative responses from all MCP providers.
		""";

    @Bean
    public CommandLineRunner predefinedQuestions(ChatClient chatClient,
                                                 WeatherTools weatherTools,
                                                 FunctionToolCallback functionToolCallback,

                                                 ToolCallbackProvider mcpToolProvider) { // (3)
        return args -> System.out.println(
                chatClient

                        .prompt(userPrompt) // (4)

                        .toolContext(Map.of("progressToken", "token-" + new Random().nextInt())) // (5)
//                        .toolCallbacks(mcpToolProvider) // (6)
//                        .tools(weatherTools)
//                        .tools(weatherTools, functionToolCallback, mcpToolProvider)
                        .tools(weatherTools, functionToolCallback)

                        .call()
                        .content());
    }
//output : The current weather in Pune is sunny, and the current time is 12:08 PM on October 2, 2026.
}
