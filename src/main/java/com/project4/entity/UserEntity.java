package com.project4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "UserEntity.getUsers", query = "select new com.project4.dto.UserDto(u.id, u.firstName, u.lastName, u.birthday, u.phone, u.email, u.password, u.address, u.status, u.role.name) from UserEntity u where u.email <> 'huynhquoctuan200702@gmail.com'")
@NamedQuery(name = "UserEntity.findByEmail", query = "select u from UserEntity u where u.email=:email")
@NamedQuery(name = "UserEntity.findByPhone", query = "select u from UserEntity u where u.phone=:phone")
@NamedQuery(name = "UserEntity.findByEmailOrPhone", query = "select u from UserEntity u where u.email=:username or u.phone=:username")
@NamedQuery(name = "UserEntity.updateStatus", query = "update UserEntity u set u.status=:status where u.id=:id")
@NamedQuery(name = "UserEntity.changePassword", query = "update UserEntity u set u.password=:newPassword where u.id=:id")
@NamedQuery(name = "UserEntity.getUser", query = "select new com.project4.dto.ProfileDto(u.id, u.firstName, u.lastName, u.birthday, u.phone, u.email, u.address) from UserEntity u where u.id=:id")
@NamedQuery(name = "UserEntity.updateProfileByUser", query = "update UserEntity u set u.firstName=:fName, u.lastName=:lName, u.birthday=:birthday, u.address=:address where u.id=:id")
@NamedQuery(name = "UserEntity.changeRole", query = "update UserEntity u set u.role=:role where u.id=:id")


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

    private LocalDate birthday;

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
