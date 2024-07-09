package com.example.task_tracker.service.impl;

import com.example.task_tracker.mapper.UserMapper;
import com.example.task_tracker.repository.UserRepository;
import com.example.task_tracker.service.UserService;
import com.example.task_tracker.web.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    @Override
    public Flux<UserModel> findAll() {
        return repository.findAll()
                .map(userMapper::userToUserModel);
    }

    @Override
    public Mono<UserModel> findById(String id) {
        return repository.findById(id)
                .map(userMapper::userToUserModel);
    }

    @Override
    public Mono<UserModel> create(UserModel model) {
        return repository.save(userMapper.userModelToUser(model))
                .map(userMapper::userToUserModel);
    }

    @Override
    public Mono<UserModel> update(String id, UserModel model) {
        return repository.findById(id)
                .flatMap(user -> {
                    user.setEmail(model.getEmail());
                    user.setUsername(model.getUsername());
                    return repository.save(user).map(userMapper::userToUserModel);}
                );
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }
}
