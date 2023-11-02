package com.pizza.backend.entites;

import com.pizza.backend.entites.keys.OrderedProductKey;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "ordered_products")
public class OrderedProduct
{
  @EmbeddedId
  private OrderedProductKey id;

  @ManyToOne
  @MapsId("order_id")
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @ManyToOne
  @MapsId("product_id")
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(name = "quantity", nullable = false)
  @DecimalMin(value = "1")
  @DecimalMax(value = "10")
  private int quantity;
}
