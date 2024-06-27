package com.example.task_tracker.mapper;

import com.example.task_tracker.entity.Task;
import com.example.task_tracker.service.UserService;
import com.example.task_tracker.web.model.TaskModel;
import com.example.task_tracker.web.model.UpsertTaskRequest;
import lombok.RequiredArgsConstructor;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

//@DecoratedWith(TaskMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    Task requestToTask(UpsertTaskRequest request);

    Task taskModelToTask(TaskModel model);

    TaskModel taskToTaskModel(Task task);


}
