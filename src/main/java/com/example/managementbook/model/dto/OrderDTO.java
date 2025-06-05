package com.example.managementbook.model.dto;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDate orderDate;
    private LocalDate receiveDate;
    private Boolean status;
    private Long userId;
}
