package com.project4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "UserEntity.findByEmail", query = "select u from UserEntity u where u.email=:email")
@NamedQuery(name = "UserEntity.findByPhone", query = "select u from UserEntity u where u.phone=:phone")
@NamedQuery(name = "UserEntity.findByEmailOrPhone", query = "select u from UserEntity u where u.email=:username or u.phone=:username")

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String firstName;

    @Column(length = 10)
    private String lastName;

    private Timestamp birthday;

    @Column(length = 11)
    private String phone;

    @Column(length = 50)
    private String email;

    @Column(length = 64)
    private String password;

    private String address;

    private String status;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "userCart")
    private List<CartEntity> userCarts = new ArrayList<>();

    @OneToMany(mappedBy = "userOrder")
    private List<OrderEntity> userOrders = new ArrayList<>();
}
