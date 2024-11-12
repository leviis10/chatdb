package com.levi.chatdb.models;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.levi.chatdb.repositories.RoleRepository;
import com.levi.chatdb.utils.enums.UserRole;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleInitializer {
    private final RoleRepository roleRepository;

    @EventListener
    @Transactional
    public void onApplicationRefreshedEvent(ContextRefreshedEvent event) {
        for (UserRole userRole : UserRole.values()) {
            if (roleRepository.findByName(userRole).isPresent()) {
                continue;
            }

            roleRepository.save(Role.builder()
                    .name(userRole)
                    .build());
        }
    }
}
