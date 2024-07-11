package com.example.task_tracker.service.impl;

import com.example.task_tracker.entity.Task;
import com.example.task_tracker.entity.User;
import com.example.task_tracker.exception.TaskNotFoundException;
import com.example.task_tracker.mapper.TaskMapper;
import com.example.task_tracker.repository.TaskRepository;
import com.example.task_tracker.service.TaskService;
import com.example.task_tracker.service.UserService;
import com.example.task_tracker.web.model.UpsertTaskRequest;
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
    public Flux<Task> findAll() {
        return taskRepository.findAll()
                .flatMap(task->getModelWithUsers(Mono.just(task)))
                .switchIfEmpty(Mono.error(new TaskNotFoundException("Task not found")));
    }

    @Override
    public Mono<Task> findById(String id) {
       return getModelWithUsers(taskRepository.findById(id))
               .switchIfEmpty(Mono.error(new TaskNotFoundException("Task not found")));
    }

    @Override
    public Mono<Task> create(UpsertTaskRequest request) {
        Task task = taskMapper.requestToTask(request);
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        return getModelWithUsers(taskRepository.save(task))
                .switchIfEmpty(Mono.error(new TaskNotFoundException("Task not found")));
    }

    @Override
    public Mono<Task> update(String id, UpsertTaskRequest request) {
        return getModelWithUsers(findById(id)
                .flatMap(existedTask -> {
                    existedTask.setName(request.getName());
                    existedTask.setDescription(request.getDescription());
                    existedTask.setUpdatedAt(Instant.now());
                    existedTask.setStatus(request.getStatus());
                    return taskRepository.save(existedTask
//                            taskMapper.taskModelToTask(existedTask)
                    );
                })).switchIfEmpty(Mono.error(new TaskNotFoundException("Task not found")));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAll() {
        return taskRepository.deleteAll();
    }


    public Mono<Task> getModelWithUsers(Mono<Task> taskMono) {

        Mono<Task> cachedTaskMono = taskMono.cache();

        Mono<User> userAuthorMono = cachedTaskMono
                .flatMap(task -> userService.findById(task.getAuthorId()));

        Mono<User> userAssigneeMono = cachedTaskMono
                .flatMap(task -> userService.findById(task.getAssigneeId()));

        Mono<Set<User>> userSetMono = cachedTaskMono.map(Task::getObserverIds)
                .flatMapMany(Flux::fromIterable)
                .flatMap(userService::findById)
                .collect(Collectors.toSet());

//        Mono<TaskModel> taskModelMono = cachedTaskMono
//                .map(taskMapper::taskToTaskModel);

        return Mono.zip(taskMono, userAuthorMono, userAssigneeMono, userSetMono)
                .map(tuple -> {
                    Task taskModel = tuple.getT1();
                    User author = tuple.getT2();
                    User assignee = tuple.getT3();
                    Set<User> userModels = tuple.getT4();
                    taskModel.setAuthor(author);
                    taskModel.setAssignee(assignee);
                    taskModel.setObservers(userModels);
                    return taskModel;
                });
    }

}
