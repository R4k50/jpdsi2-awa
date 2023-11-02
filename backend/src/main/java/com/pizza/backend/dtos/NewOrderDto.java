package com.pizza.backend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewOrderDto
{
  @NotNull(message = "Products must not be empty")
  private List<@Valid OrderedProductDto> products;

  @NotEmpty(message = "Address must not be empty")
  @Size(min = 2, max = 200, message = "Address must be between 2 and 200 characters")
  private String address;

  @NotEmpty(message = "City must not be empty")
  @Size(min = 2, max = 200, message = "City must be between 2 and 200 characters")
  private String city;

  @NotEmpty(message = "Postal code must not be empty")
  @Size(min = 2, max = 200, message = "Postal code must be between 2 and 200 characters")
  private String postalCode;

  @Size(min = 2, max = 200, message = "Notes field must be between 2 and 200 characters")
  private String notes;
}
