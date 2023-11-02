package com.pizza.backend.dtos;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
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
public class FullOrderDto
{
  @NotEmpty(message = "Id must not be empty")
  @Id
  private Long id;

  @NotNull(message = "Products must not be null")
  private List<@Valid OrderedProductDto> products;

  @NotEmpty(message = "userId must not be empty")
  private Long userId;

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

  @NotEmpty(message = "deliveryManId must not be empty")
  private Long deliveryManId;
}
