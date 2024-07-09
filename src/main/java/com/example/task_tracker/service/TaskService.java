package com.example.task_tracker.service;

import com.example.task_tracker.web.model.TaskModel;
import com.example.task_tracker.web.model.UpsertTaskRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TaskService {

    Flux<TaskModel>findAll();
    Mono<TaskModel> findById(String id);
    Mono<TaskModel> create(UpsertTaskRequest request);
    Mono<TaskModel> update(String id, UpsertTaskRequest request);
    Mono<Void> deleteById(String id);
    Mono<Void> deleteAll();
}
