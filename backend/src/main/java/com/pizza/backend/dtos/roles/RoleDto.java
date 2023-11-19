package com.pizza.backend.dtos.roles;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto
{
  @NotEmpty(message = "Role must not be empty")
  @Size(min = 2, max = 50, message = "Role must be between 2 and 50 characters")
  private String role;
}
