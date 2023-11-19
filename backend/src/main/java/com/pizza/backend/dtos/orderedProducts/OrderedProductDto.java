package com.pizza.backend.dtos.orderedProducts;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderedProductDto
{
  @NotNull(message = "Id must not be empty")
  private Long id;

  @NotNull(message = "Quantity must not be empty")
  @Range(min = 1, max = 10, message = "Quantity must be a number between 1 and 10")
  private int quantity;
}
