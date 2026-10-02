package com.pankaj.ai.mcp.server.first;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIToolController {

    public  AIToolController() {
        System.out.println("AIToolController-------------------------------------------");
    }

//    @Autowired
//    ToolCallbackProvider toolCallbackProvider;
}
