package com.levi.chatdb.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.levi.chatdb.models.Role;
import com.levi.chatdb.repositories.RoleRepository;
import com.levi.chatdb.utils.enums.UserRole;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public List<Role> findByNames(List<UserRole> role) {
        return roleRepository.findByNameIn(role);
    }
}
