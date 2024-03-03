package com.project4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64)
    private String image;

    @Column(length = 50)
    private String name;

    private int quantity;

    private double price;

    @Column(length = 1000)
    private String description;

    private boolean status;

    private String createBy;

    private Timestamp createAt;

    private String modifyBy;

    private Timestamp modifyAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "productCart")
    private List<CartEntity> productCarts = new ArrayList<>();
}
