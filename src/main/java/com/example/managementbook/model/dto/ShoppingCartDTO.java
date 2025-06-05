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
    private Long productId;
}