package com.example.task_tracker.service;

import com.example.task_tracker.entity.User;
import com.example.task_tracker.web.model.UserModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserModel> findAll();
    Mono<UserModel> findById(String id);
    Mono<UserModel> create(UserModel model);
    Mono<UserModel> update(String id, UserModel model);
    Mono<Void> deleteById(String id);
}
