package com.project4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NamedQuery(name = "CartEntity.getCarts", query = "select new com.project4.dto.CartDto(c.id, c.productCart.image, c.productCart.price, c.quantity, c.total) from CartEntity c")
@NamedQuery(name = "CartEntity.getCartsByUser", query = "select new com.project4.dto.CartDto(c.id, c.productCart.image, c.productCart.price, c.quantity, c.total) from CartEntity c where c.userCart.id=:user_id")

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
