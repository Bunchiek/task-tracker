package com.example.task_tracker.service;

import com.example.task_tracker.entity.RoleType;
import com.example.task_tracker.entity.User;
import com.example.task_tracker.web.model.UserModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<User> findAll();
    Mono<User> findById(String id);
    Mono<User> create(UserModel model, RoleType role);
    Mono<User> update(String id, UserModel model);
    Mono<Void> deleteById(String id);
    User findByUsername(String username);
}
