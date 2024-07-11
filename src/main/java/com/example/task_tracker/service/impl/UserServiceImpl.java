package com.example.task_tracker.service.impl;

import com.example.task_tracker.entity.RoleType;
import com.example.task_tracker.entity.User;
import com.example.task_tracker.exception.UserNotFoundException;
import com.example.task_tracker.mapper.UserMapper;
import com.example.task_tracker.repository.UserRepository;
import com.example.task_tracker.service.UserService;
import com.example.task_tracker.web.model.UserModel;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Flux<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<User> findById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
    }

    @Override
    public Mono<User> create(UserModel model, RoleType role) {
        Set<RoleType> roles = new HashSet<>();
        roles.add(role);
        model.setRoles(roles);
        model.setPassword(passwordEncoder.encode(model.getPassword()));

        return repository.save(userMapper.userModelToUser(model));
    }

    @Override
    public Mono<User> update(String id, UserModel model) {
        return repository.findById(id)
                .flatMap(user -> {
                    user.setEmail(model.getEmail());
                    user.setUsername(model.getUsername());
                    return repository.save(user);
                }
                );
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }


    @Override
    public User findByUsername(String username) {
        User user = repository.findByUsername(username);
        return user;
    }
}
