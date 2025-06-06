package com.example.managementbook.controller;
import com.example.managementbook.model.dto.OrderDetailDTO;
import com.example.managementbook.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/order-details")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetailsByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderDetailService.getAllOrderDetailsByOrder(orderId));
    }
}