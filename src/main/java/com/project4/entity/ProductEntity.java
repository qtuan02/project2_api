package com.project4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "ProductEntity.getProducts", query = "select new com.project4.dto.ProductDto(p.id, p.image, p.name, p.quantity, p.price, p.description, p.status, p.createBy, p.createAt, p.modifyBy, p.modifyAt, p.category.name) from ProductEntity p")
@NamedQuery(name = "ProductEntity.getProductsByCategory", query = "select new com.project4.dto.ProductDto(p.id, p.image, p.name, p.quantity, p.price, p.description, p.status, p.createBy, p.createAt, p.modifyBy, p.modifyAt, p.category.name) from ProductEntity p where p.category.id=:id")
@NamedQuery(name = "ProductEntity.changsStatus", query = "update ProductEntity p set p.status=:status where p.id=:id")


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

    @Column(length = 70)
    private String image;

    @Column(length = 50)
    private String name;

    private int quantity;

    private double price;

    @Column(length = 1000)
    private String description;

    private String status;

    private String createBy;

    private LocalDateTime createAt;

    private String modifyBy;

    private LocalDateTime modifyAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "productCart")
    private List<CartEntity> productCarts = new ArrayList<>();
}
