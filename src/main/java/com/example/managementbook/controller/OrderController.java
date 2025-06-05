package com.example.managementbook.controller;
import com.example.managementbook.model.dto.OrderDTO;
import com.example.managementbook.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getAllOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getAllOrdersByUser(userId));
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<OrderDTO> checkout(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.checkout(userId));
    }

    @PutMapping("/status/{orderId}")
    public ResponseEntity<OrderDTO> changeOrderStatus(@PathVariable Long orderId, @RequestParam Boolean status) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, status));
    }
}