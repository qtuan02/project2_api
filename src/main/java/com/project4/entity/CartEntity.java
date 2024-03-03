package com.project4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private double total;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productCart;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userCart;
}
