package com.example.task_tracker.web.controller;

import com.example.task_tracker.mapper.TaskMapper;
import com.example.task_tracker.service.TaskService;
import com.example.task_tracker.web.model.TaskModel;
import com.example.task_tracker.web.model.UpsertTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public Flux<TaskModel> findAllTasks() {
        return taskService.findAll()
                .map(taskMapper::taskToTaskModel);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TaskModel>> getById(@PathVariable String id) {
        return taskService.findById(id)
                .map(taskMapper::taskToTaskModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<TaskModel>> createTask(@RequestBody UpsertTaskRequest request) {
        return taskService.create(request)
                .map(taskMapper::taskToTaskModel)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<TaskModel>> updateTask(@PathVariable String id, @RequestBody UpsertTaskRequest request) {
        return taskService.update(id,request)
                .map(taskMapper::taskToTaskModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTask(@PathVariable String id) {
        return taskService.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }

    @DeleteMapping()
    public Mono<ResponseEntity<Void>> deleteAll() {
        return taskService.deleteAll().then(Mono.just(ResponseEntity.noContent().build()));
    }

}
