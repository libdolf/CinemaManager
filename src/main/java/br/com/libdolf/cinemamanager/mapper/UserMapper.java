package br.com.libdolf.cinemamanager.mapper;

import br.com.libdolf.cinemamanager.controllers.user.dtos.RegisterUserRequest;
import br.com.libdolf.cinemamanager.controllers.user.dtos.UserRequest;
import br.com.libdolf.cinemamanager.controllers.user.dtos.UserResponse;
import br.com.libdolf.cinemamanager.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "password", ignore = true) })
    public abstract User toUser(UserRequest request);

    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "password", ignore = true) })
    public abstract User toUser(RegisterUserRequest request);

    @Mappings({
            @Mapping(target = "rolesNames", expression = "java(user.getRolesNames())"),
            @Mapping(target = "userId", expression = "java(user.getUserId().toString())")})
    public abstract UserResponse toResponse(User user);
}
