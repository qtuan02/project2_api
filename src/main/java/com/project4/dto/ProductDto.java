package com.project4.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;

    private String image;

    private String name;

    private int quantity;

    private double price;

    private String description;

    private String status;

    private String createBy;

    private LocalDateTime createAt;

    private String modifyBy;

    private LocalDateTime modifyAt;

    private String category;
}
