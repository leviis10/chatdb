package com.levi.chatdb.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.chatdb.services.AuthService;
import com.levi.chatdb.utils.contants.Endpoint;
import com.levi.chatdb.utils.dtos.requests.LoginDTO;
import com.levi.chatdb.utils.dtos.requests.RegisterDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Endpoint.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterDTO request) {
        String token = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO request) {
        Map<String, String> response = authService.login(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
