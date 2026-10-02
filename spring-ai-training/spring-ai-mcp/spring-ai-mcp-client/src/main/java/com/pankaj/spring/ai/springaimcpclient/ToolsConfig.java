package com.pankaj.spring.ai.springaimcpclient;

import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ToolsConfig {

    @Bean
    public FunctionToolCallback systemTimeTool() {
        return FunctionToolCallback.builder("getSystemTime", () -> java.time.Instant.now().toString())
                .description("Gets the current system time")
                .build();
    }
}
