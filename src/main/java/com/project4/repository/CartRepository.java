package com.project4.repository;

import com.project4.dto.CartDto;
import com.project4.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    List<CartDto> getCartsByUser(Long user_id);
    List<CartDto> getCarts();
}
