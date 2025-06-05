package com.example.managementbook.model.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private int stock;
    private Boolean status;
    private Long categoryId;
}