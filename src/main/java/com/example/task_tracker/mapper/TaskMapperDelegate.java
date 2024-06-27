package com.example.task_tracker.mapper;

import com.example.task_tracker.entity.Task;
import com.example.task_tracker.entity.User;
import com.example.task_tracker.service.UserService;
import com.example.task_tracker.web.model.TaskModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class TaskMapperDelegate implements TaskMapper{


//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserMapper userMapper;


//    @Override
//    public TaskModel taskToTaskModel(Task task) {
//
//        Mono<User> author = userService.findById(task.getAuthorId());
//        Mono<User> assignee = userService.findById(task.getAssigneeId());
//
//        TaskModel model = new TaskModel();
//
//        model.setId(task.getId());
//        model.setName(task.getName());
//        model.setDescription(task.getDescription());
//        model.setStatus(task.getStatus());
//        model.setCreatedAt(task.getCreatedAt());
//        model.setUpdatedAt(task.getUpdatedAt());
//        Mono.zip(author, assignee)
//                .map(users-> {
//                    model.setAuthor(users.getT1());
//                    model.setAssignee(users.getT2());
//                    return model;
//                        }
//                );
////        model.setObservers());
//        return model;
//    }
}
