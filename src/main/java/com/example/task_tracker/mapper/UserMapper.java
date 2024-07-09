package com.example.task_tracker.mapper;

import com.example.task_tracker.entity.User;
import com.example.task_tracker.web.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User userModelToUser(UserModel model);

    @Mapping(source = "userId", target = "id")
    User userModelToUser(String userId, UserModel model);

    UserModel userToUserModel(User user);
}
