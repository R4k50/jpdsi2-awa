package com.pizza.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserDto
{
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotEmpty(message = "Surname must not be empty")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String surname;

    @NotEmpty(message = "Email must not be empty")
    @Size(min = 2, max = 50, message = "Email must be between 2 and 50 characters")
    @Email(message = "Invalid email")
    private String email;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;
}
