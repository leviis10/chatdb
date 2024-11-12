package com.levi.chatdb.services;

import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.levi.chatdb.models.Role;
import com.levi.chatdb.models.User;
import com.levi.chatdb.securities.JWTUtils;
import com.levi.chatdb.utils.dtos.requests.LoginDTO;
import com.levi.chatdb.utils.dtos.requests.RegisterDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterDTO request) {
        List<Role> roles = roleService.findByNames(request.getRoles());

        User newUser = userService.create(User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build());

        return jwtUtils.generateToken(newUser.getUsername());
    }

    public Map<String, String> login(LoginDTO request) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = (User) auth.getPrincipal();

        String token = jwtUtils.generateToken(request.getUsername());

        return Map.of("token", token, "id", user.getId().toString());
    }
}
