package com.pizza.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto
{
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String token;
    private List<String> roles;
}
