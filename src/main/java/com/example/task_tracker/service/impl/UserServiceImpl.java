package com.example.task_tracker.service.impl;

import com.example.task_tracker.entity.RoleType;
import com.example.task_tracker.entity.User;
import com.example.task_tracker.exception.UserNotFoundException;
import com.example.task_tracker.mapper.UserMapper;
import com.example.task_tracker.repository.UserRepository;
import com.example.task_tracker.service.UserService;
import com.example.task_tracker.web.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;


    @Override
    public Flux<User> findAll() {
        return repository.findAll()
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
//                .map(userMapper::userToUserModel);
    }

    @Override
    public Mono<User> findById(String id) {
        return repository.findById(id)
//                .map(userMapper::userToUserModel)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
    }

    @Override
    public Mono<User> create(UserModel model, RoleType roleType) {
        model.setRoles();
        return repository.save(userMapper.userModelToUser(model));
//                .map(userMapper::userToUserModel);
    }

    @Override
    public Mono<User> update(String id, UserModel model) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")))
                .flatMap(user -> {
                    user.setEmail(model.getEmail());
                    user.setUsername(model.getUsername());
                    return repository.save(user);
//                            .map(userMapper::userToUserModel);
                }
                );
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }


    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
