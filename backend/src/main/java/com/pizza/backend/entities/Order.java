package com.pizza.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "orders")
public class Order
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "address", nullable = false)
  @Size(min = 2, max = 200)
  private String address;

  @Column(name = "city", nullable = false)
  @Size(min = 2, max = 200)
  private String city;

  @Column(name = "postal_code", nullable = false)
  @Size(min = 2, max = 200)
  private String postalCode;

  @Column(name = "notes", nullable = true)
  @Size(min = 2, max = 200)
  private String notes;

  @ManyToOne
  @JoinColumn(name = "delivery_man_id", nullable = true)
  private User deliveryMan;
}
