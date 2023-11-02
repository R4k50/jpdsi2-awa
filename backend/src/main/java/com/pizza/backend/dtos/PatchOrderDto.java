package com.pizza.backend.dtos;

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
public class PatchOrderDto
{
  private Long userId;

  @Size(min = 2, max = 200, message = "Address must be between 2 and 200 characters")
  private String address;

  @Size(min = 2, max = 200, message = "City must be between 2 and 200 characters")
  private String city;

  @Size(min = 2, max = 200, message = "Postal code must be between 2 and 200 characters")
  private String postalCode;

  @Size(min = 2, max = 200, message = "Notes field must be between 2 and 200 characters")
  private String notes;

  private Long deliveryManId;
}
