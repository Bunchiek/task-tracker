package com.example.task_tracker.web.controller;

import com.example.task_tracker.entity.RoleType;
import com.example.task_tracker.exception.UserNotFoundException;
import com.example.task_tracker.mapper.UserMapper;
import com.example.task_tracker.service.UserService;
import com.example.task_tracker.web.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;


    @GetMapping
    public Flux<UserModel> getAllUsers() {
        return userService.findAll()
                .map(userMapper::userToUserModel);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public Mono<ResponseEntity<UserModel>> getById(@PathVariable String id, Mono<Principal> principal) {
        return userService.findById(id)
                .map(userMapper::userToUserModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UserModel>> createUser(@RequestBody UserModel model, @RequestParam RoleType roleType) {
        return userService.create(model, roleType)
                .map(userMapper::userToUserModel)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserModel>> updateUser(@PathVariable String id, @RequestBody UserModel model) {
        return userService.update(id,model)
                .map(userMapper::userToUserModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id) {
        return userService.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/test")
    public UserModel getAllUsers(@RequestParam String name) {
        return userMapper.userToUserModel(userService.findByUsername(name));
    }


}
