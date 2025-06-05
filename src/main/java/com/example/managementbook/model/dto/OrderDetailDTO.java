package com.example.managementbook.model.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private Long id;
    private int quantity;
    private double unitPrice;
    private Long productId;
    private Long orderId;
}
