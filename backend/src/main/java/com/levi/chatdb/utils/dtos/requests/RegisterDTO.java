package com.levi.chatdb.utils.dtos.requests;

import java.util.List;

import com.levi.chatdb.utils.enums.UserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterDTO {
    @NotBlank(message = "Username cannot be blank.")
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    private String password;

    @Size(min = 1, message = "Roles must contains at least 1 value.")
    private List<UserRole> roles;
}
