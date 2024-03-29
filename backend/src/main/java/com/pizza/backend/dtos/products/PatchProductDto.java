package com.pizza.backend.dtos.products;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatchProductDto
{
  @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
  private String name;

  @Size(min = 2, max = 200, message = "Ingredients field must be between 2 and 200 characters")
  private String ingredients;

  @DecimalMin(value = "1.99", inclusive = true, message = "Price must be at least 2.00")
  @DecimalMax(value = "999.99", inclusive = true, message = "Price must be at most 999.99")
  @Digits(integer = 3, fraction = 2, message = "Price must have exactly two decimal places")
  private BigDecimal price;

  @Size(min = 5, max = 100)
  private String img;
}
