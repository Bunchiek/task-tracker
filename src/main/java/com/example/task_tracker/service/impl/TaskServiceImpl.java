package com.example.task_tracker.service.impl;

import com.example.task_tracker.entity.Task;
import com.example.task_tracker.entity.User;
import com.example.task_tracker.exception.TaskNotFoundException;
import com.example.task_tracker.exception.UserNotFoundException;
import com.example.task_tracker.mapper.TaskMapper;
import com.example.task_tracker.mapper.UserMapper;
import com.example.task_tracker.repository.TaskRepository;
import com.example.task_tracker.service.TaskService;
import com.example.task_tracker.service.UserService;
import com.example.task_tracker.web.model.TaskModel;
import com.example.task_tracker.web.model.UpsertTaskRequest;
import com.example.task_tracker.web.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;

    @Override
    public Flux<TaskModel> findAll() {
        return taskRepository.findAll()
                .flatMap(task->getModelWithUsers(Mono.just(task)));
    }

    @Override
    public Mono<TaskModel> findById(String id) {
       return getModelWithUsers(taskRepository.findById(id));
    }

    @Override
    public Mono<TaskModel> create(UpsertTaskRequest request) {
        Task task = taskMapper.requestToTask(request);
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        return getModelWithUsers(taskRepository.save(task));
    }

    @Override
    public Mono<TaskModel> update(String id, UpsertTaskRequest request) {
        return getModelWithUsers(findById(id)
                .flatMap(existedTask -> {
                    existedTask.setName(request.getName());
                    existedTask.setDescription(request.getDescription());
                    existedTask.setUpdatedAt(Instant.now());
                    existedTask.setStatus(request.getStatus());
                    return taskRepository.save(taskMapper.taskModelToTask(existedTask));
                }));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAll() {
        return taskRepository.deleteAll();
    }


    public Mono<TaskModel> getModelWithUsers(Mono<Task> taskMono) {

        Mono<Task> cachedTaskMono = taskMono.cache();

        Mono<UserModel> userAuthorMono = cachedTaskMono
                .flatMap(task -> userService.findById(task.getAuthorId()));

        Mono<UserModel> userAssigneeMono = cachedTaskMono
                .flatMap(task -> userService.findById(task.getAssigneeId()));

        Mono<Set<UserModel>> userSetMono = cachedTaskMono.map(Task::getObserverIds)
                .flatMapMany(Flux::fromIterable)
                .flatMap(userService::findById)
                .collect(Collectors.toSet());

        Mono<TaskModel> taskModelMono = cachedTaskMono
                .map(taskMapper::taskToTaskModel);

        return Mono.zip(taskModelMono, userAuthorMono, userAssigneeMono, userSetMono)
                .map(tuple -> {
                    TaskModel taskModel = tuple.getT1();
                    UserModel author = tuple.getT2();
                    UserModel assignee = tuple.getT3();
                    Set<UserModel> userModels = tuple.getT4();
                    taskModel.setAuthor(author);
                    taskModel.setAssignee(assignee);
                    taskModel.setObservers(userModels);
                    return taskModel;
                });
    }

}
