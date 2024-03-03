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
@Table(name = "tbl_orderdetail")
public class OrderdetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String image;

    @Column(length = 50)
    private String name;

    private int quantity;

    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
