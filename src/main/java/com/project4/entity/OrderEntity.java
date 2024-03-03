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
@Table(name = "tbl_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp createAt;

    private String status;

    private double total;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userOrder;

    @OneToMany(mappedBy = "order")
    private List<OrderdetailEntity> orderDetails = new ArrayList<>();
}
