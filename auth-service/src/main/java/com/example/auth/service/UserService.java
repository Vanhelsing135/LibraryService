package com.example.auth.service;

import com.example.auth.dto.UserDTO;
import com.example.auth.entity.User;
import com.example.auth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {
    private final UserRepository repository;

    public List<User> allUsers(){
        return new ArrayList<>(repository.findAll());
    }


    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }

        return repository.save(user);
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }


    public void deleteUser(String username) {
        var user = repository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        repository.delete(user);
    }


    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}