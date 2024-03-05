package com.project4.controller;

import com.project4.dto.ProfileDto;
import com.project4.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface IUserController {
    @PostMapping(path = "/register")
    ResponseEntity<String> register(@RequestBody(required = true)Map<String, String> requestMap);
    @PostMapping(path = "/login")
    ResponseEntity<String> login(@RequestBody(required = true)Map<String, String> requestMap);
    @PostMapping(path = "/changeStatus")
    ResponseEntity<String> changeStatus(@RequestBody(required = true)Map<String, String> requestMap);
    @PostMapping(path = "/changePassword")
    ResponseEntity<String> changePassword(@RequestBody(required = true)Map<String, String> requestMap);
    @PostMapping(path = "/changeRole")
    ResponseEntity<String> changeRole(@RequestBody Map<String, String> requestMap);
    @GetMapping(path = "/getUsers")
    ResponseEntity<List<UserDto>> getUsers();
    @GetMapping(path = "/getUser/{id}")
    ResponseEntity<ProfileDto> getUser(@PathVariable Long id);
    @PostMapping(path = "/updateProfile")
    ResponseEntity<String> updateProfile(@RequestBody Map<String, String> requestMap);
}
