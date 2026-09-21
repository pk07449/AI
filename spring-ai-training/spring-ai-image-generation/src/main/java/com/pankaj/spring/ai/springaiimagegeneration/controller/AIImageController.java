package com.pankaj.spring.ai.springaiimagegeneration.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIImageController {

    private ImageModel imageModel;
    private ChatClient chatClient;
    public AIImageController(ImageModel imageModel){
        this.imageModel = imageModel;
    }
//@GetMapping("/olama/message")
//public String olamaImageChat(
//        @RequestParam(value = "message", defaultValue = "is patna capital of bihar ?") String message) {
//   return chatClient.prompt().call().te
//           chatResponse().getResult().getOutput().getText();
//
//}
}
