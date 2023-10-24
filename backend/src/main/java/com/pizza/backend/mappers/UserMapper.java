package com.pizza.backend.mappers;

import com.pizza.backend.dtos.RegisterDto;
import com.pizza.backend.dtos.UserDto;
import com.pizza.backend.entites.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper
{
    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User registerToUser(RegisterDto registerDto);

}
