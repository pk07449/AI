package com.pankaj.spring.ai.springaimcpclient;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class WeatherTools {



    @Tool(description = "Get the current weather for a location")
    public String getCurrentWeather(String location) {
        // Your logic here
        return "The weather in " + location + " is sunny.";
    }
}