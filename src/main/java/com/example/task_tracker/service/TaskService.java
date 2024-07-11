package com.example.task_tracker.service;

import com.example.task_tracker.entity.Task;
import com.example.task_tracker.web.model.TaskModel;
import com.example.task_tracker.web.model.UpsertTaskRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TaskService {

    Flux<Task>findAll();
    Mono<Task> findById(String id);
    Mono<Task> create(UpsertTaskRequest request);
    Mono<Task> update(String id, UpsertTaskRequest request);
    Mono<Void> deleteById(String id);
    Mono<Void> deleteAll();
}
