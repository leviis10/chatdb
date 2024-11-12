package com.levi.chatdb.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.levi.chatdb.models.User;
import com.levi.chatdb.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findByUsername(username).orElseThrow();
        Hibernate.initialize(foundUser.getRoles());
        return foundUser;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public List<User> getAll() {
        List<User> foundUsers = userRepository.findAll();
        foundUsers.forEach(user -> Hibernate.initialize(user.getRoles()));
        return foundUsers;
    }

    public List<User> getAllById(List<Long> ids) {
        return userRepository.findAllById(ids).stream().toList();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
