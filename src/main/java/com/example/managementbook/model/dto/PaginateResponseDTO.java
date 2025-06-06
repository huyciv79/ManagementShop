package com.example.managementbook.model.dto;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaginateResponseDTO<T> {
    private Long total;
    private int page;
    private int size;
    private List<T> data;

}
