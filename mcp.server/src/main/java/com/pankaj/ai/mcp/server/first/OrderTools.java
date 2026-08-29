package com.pankaj.ai.mcp.server.first;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
 
@Component
public class OrderTools {

    @Autowired OrderService orderService;
    @McpTool(
        name        = "get_order_status",
        description = "Returns the current status of an order. Use this when the user asks " +
                      "about an order's state, delivery progress, or shipment tracking."
    )
    public OrderStatus getOrderStatus(
        @McpToolParam(description = "The order ID to look up, e.g. ORD-00123", required = true)
        String orderId
    ) {
        // real implementation queries your order service / database
        return new OrderStatus(orderId, "SHIPPED", "2026-06-04");
    }
 
    @McpTool(
        name        = "list_recent_orders",
        description = "Returns the most recent orders for a customer. Use when the user asks " +
                      "to see their order history or recent purchases."
    )
    public List<OrderSummary> listRecentOrders(
        @McpToolParam(description = "Customer ID", required = true)
        String customerId,
        @McpToolParam(description = "Maximum number of orders to return, 1-50", required = false)
        Integer limit
    ) {
        int effectiveLimit = (limit != null && limit > 0) ? Math.min(limit, 50) : 10;
        return orderService.findRecentByCustomer(customerId, effectiveLimit);
    }
 
    @McpTool(
        name        = "cancel_order",
        description = "Cancels an order that has not yet shipped. Returns a confirmation or " +
                      "an error message if cancellation is not possible."
    )
    public String cancelOrder(
        @McpToolParam(description = "Order ID to cancel", required = true)  String orderId,
        @McpToolParam(description = "Reason for cancellation", required = false) String reason
    ) {
        return orderService.cancel(orderId, reason);
    }
 
    // Return types are serialised to JSON automatically via Jackson 3.
    // Use Java records or POJOs with public getters — both work.
    public record OrderStatus(String orderId, String status, String estimatedDelivery) {}
    public record OrderSummary(String orderId, String date, double total) {}
}