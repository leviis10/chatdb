package com.levi.chatdb.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.chatdb.models.User;
import com.levi.chatdb.services.UserService;
import com.levi.chatdb.utils.contants.Endpoint;
import com.levi.chatdb.utils.dtos.responses.UserDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Endpoint.USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAll();
        List<UserDTO> response = users.stream()
                .map(user -> UserDTO.toDTO(user))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
