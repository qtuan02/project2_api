package com.project4.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IUserService {
    ResponseEntity<String> register(Map<String, String> requestMap);
    ResponseEntity<String> login(Map<String, String> requestMap);
}
