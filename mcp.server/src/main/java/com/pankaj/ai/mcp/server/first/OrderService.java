package com.pankaj.ai.mcp.server.first;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    public List<OrderTools.OrderSummary> findRecentByCustomer(String customerId, int effectiveLimit) {
        return null;
    }

    public String cancel(String orderId, String reason) {
        return null;
    }
}
