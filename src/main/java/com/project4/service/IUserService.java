package com.project4.service;

import com.project4.dto.ProfileDto;
import com.project4.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IUserService {
    ResponseEntity<String> register(Map<String, String> requestMap);
    ResponseEntity<String> login(Map<String, String> requestMap);
    ResponseEntity<String> updateStatus(Map<String, String> requestMap);
    ResponseEntity<String> changePassword(Map<String, String> requestMap);
    ResponseEntity<String> changeRole(Map<String, String> requestMap);
    ResponseEntity<List<UserDto>> getUsers();
    ResponseEntity<ProfileDto> getUser(Long id);
    ResponseEntity<String> updateProfile(Map<String, String> requestMap);
}
