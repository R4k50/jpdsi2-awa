package com.pizza.backend.mappers;

import com.pizza.backend.dtos.RegisterDto;
import com.pizza.backend.dtos.UserDto;
import com.pizza.backend.entities.Role;
import com.pizza.backend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    UserDto toUserDto(User user);

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
}
