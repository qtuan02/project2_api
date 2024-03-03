package com.project4.repository;

import com.project4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(@RequestParam(name = "email")String email);
    UserEntity findByPhone(@RequestParam(name = "phone")String phone);
    UserEntity findByEmailOrPhone(@RequestParam(name = "username")String username);

}
