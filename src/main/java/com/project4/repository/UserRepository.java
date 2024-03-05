package com.project4.repository;

import com.project4.dto.ProfileDto;
import com.project4.dto.UserDto;
import com.project4.entity.RoleEntity;
import com.project4.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByPhone(String phone);
    UserEntity findByEmailOrPhone(String username);
    @Modifying
    @Transactional
    void updateStatus(String status, Long id);
    @Modifying
    @Transactional
    void changePassword(String newPassword, Long id);
    ArrayList<UserDto> getUsers();
    ProfileDto getUser(Long id);
    @Modifying
    @Transactional
    void updateProfileByUser(Long id, String fName, String lName, LocalDate birthday, String address);
    @Modifying
    @Transactional
    void changeRole(Long id, RoleEntity role);
}
