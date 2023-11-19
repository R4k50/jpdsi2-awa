package com.pizza.backend.controllers;

import com.pizza.backend.dtos.roles.RoleDto;
import com.pizza.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class RoleController
{
    private final UserService userService;

    @PostMapping("/addRole/{userId}")
    public void addRole(@RequestBody @Valid RoleDto roleDto, @PathVariable Long userId)
    {
        userService.addRole(userId, roleDto.getRole());
    }

    @PostMapping("/removeRole/{userId}")
    public void removeRole(@RequestBody @Valid RoleDto roleDto, @PathVariable Long userId)
    {
        userService.removeRole(userId, roleDto.getRole());
    }
}
