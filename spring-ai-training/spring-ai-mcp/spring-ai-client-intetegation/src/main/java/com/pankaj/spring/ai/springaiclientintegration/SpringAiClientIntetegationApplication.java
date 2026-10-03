package com.pankaj.spring.ai.springaiclientintegration;

import io.modelcontextprotocol.client.McpClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootApplication
public class SpringAiClientIntetegationApplication {


    public static void main(String[] args) {

        SpringApplication.run(SpringAiClientIntetegationApplication.class, args);
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) { // (2)
        return chatClientBuilder.build();
    }

    String userPrompt = """
		track order id  #1234567abc for cancelation, order was placed worongly
		""";

    @Bean
    public CommandLineRunner predefinedQuestions(ChatClient chatClient,

                                                 FunctionToolCallback functionToolCallback,

                                                 ToolCallbackProvider mcpToolProvider, List<McpClient> mcpClients) { // (3)
        return args -> System.out.println(
                chatClient

                        .prompt(userPrompt) // (4)

                        .toolContext(Map.of("progressToken", "token-" + new Random().nextInt())) // (5)
//                        .toolCallbacks(mcpToolProvider) // (6)
//                        .tools(weatherTools)
//                        .tools(mcpClients)
                        .tools(mcpToolProvider)
//                        .tools(weatherTools, functionToolCallback, mcpToolProvider)
                        //                     .tools(weatherTools, functionToolCallback)

                        .call()
                        .content());
    }
//output :

}
