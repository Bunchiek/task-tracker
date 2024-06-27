package com.example.task_tracker.web.model;

import com.example.task_tracker.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpsertTaskRequest {
    private String name;
    private String description;
    private TaskStatus status;
    private String authorId;
    private String assigneeId;
    private Set<String> observerIds;
}
