package com.pizza.backend.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto
{
  @NotEmpty(message = "Id must not be empty")
  @Id
  private Long id;

  @NotNull
  private List<OrderedProductDto> products;

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
