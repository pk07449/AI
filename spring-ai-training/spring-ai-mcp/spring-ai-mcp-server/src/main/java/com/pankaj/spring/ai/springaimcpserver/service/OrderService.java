package com.pankaj.spring.ai.springaimcpserver.service;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @McpTool(description = "")
    public Order getORder(int orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        return order;
    }
}
