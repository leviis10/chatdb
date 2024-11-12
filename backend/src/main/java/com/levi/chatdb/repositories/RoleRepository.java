package com.levi.chatdb.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.levi.chatdb.models.Role;
import com.levi.chatdb.utils.enums.UserRole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole role);

    @EntityGraph(attributePaths = "users")
    List<Role> findByNameIn(List<UserRole> role);
}
