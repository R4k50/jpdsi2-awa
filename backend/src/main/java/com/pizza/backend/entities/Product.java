package com.pizza.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "products")
public class Product
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  @Size(min = 2, max = 50)
  private String name;

  @Column(name = "ingredients", nullable = false)
  @Size(min = 2, max = 200)
  private String ingredients;

  @Column(name = "price", nullable = false)
  @DecimalMin(value = "2.00", inclusive = true)
  @DecimalMax(value = "999.99", inclusive = true)
  @Digits(integer = 3, fraction = 2)
  private BigDecimal price;

  @Column(name = "img", nullable = false)
  @Size(min = 5, max = 100)
  private String img;
}
