package com.pizza.backend.mappers;

import com.pizza.backend.dtos.auth.RegisterDto;
import com.pizza.backend.dtos.users.NewUserDto;
import com.pizza.backend.dtos.users.PatchUserDto;
import com.pizza.backend.dtos.users.UserDto;
import com.pizza.backend.entities.Role;
import com.pizza.backend.entities.User;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    UserDto toUserDto(User user);
    List<UserDto> toUserDtoList(List<User> users);

    @Mapping(target = "password", ignore = true)
    User registerToUser(RegisterDto registerDto);

    default List<String> roleSetToRoleList(Set<Role> roles)
    {
        List<String> roleList = new ArrayList<>();

        for (Role role : roles)
        {
            roleList.add(role.getName());
        }

        return roleList;
    }

    User newUserDtoToUser(NewUserDto newUserDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget User user, PatchUserDto patchUserDto);
}
