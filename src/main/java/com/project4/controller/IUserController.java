package com.project4.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/user")
public interface IUserController {
    @PostMapping(path = "/register")
    ResponseEntity<String> Register(@RequestBody(required = true)Map<String, String> requestMap);
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap);
}
