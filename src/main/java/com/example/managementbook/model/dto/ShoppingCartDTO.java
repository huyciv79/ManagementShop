package com.example.managementbook.model.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDTO {
    private Long id;
    private int quantity;
    private Long userId;
    private String userName;
    private Long productId;
    private String productName;
}