package com.example.task_tracker.web.model;

import com.example.task_tracker.entity.TaskStatus;
import com.example.task_tracker.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskModel {
    private String id;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private TaskStatus status;

    private User author;
    private User assignee;
    private Set<UserModel> observers;
}
