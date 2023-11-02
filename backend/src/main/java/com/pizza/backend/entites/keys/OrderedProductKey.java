package com.pizza.backend.entites.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderedProductKey implements Serializable
{
  @Column(name = "order_id", nullable = false)
  private Long orderId;

  @Column(name = "product_id", nullable = false)
  private Long productId;
}
