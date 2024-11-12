package com.levi.chatdb.utils.dtos.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.levi.chatdb.models.Role;
import com.levi.chatdb.models.User;
import com.levi.chatdb.utils.enums.UserRole;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;

    private String username;

    private List<UserRole> roles;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(getRoles(user.getRoles()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static List<UserRole> getRoles(List<Role> roles) {
        return roles.stream()
                .map(role -> role.getName())
                .toList();
    }
}
