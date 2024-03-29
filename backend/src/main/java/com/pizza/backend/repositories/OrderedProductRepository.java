package com.pizza.backend.repositories;

import com.pizza.backend.entities.OrderedProduct;
import com.pizza.backend.entities.keys.OrderedProductKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedProductRepository extends JpaRepository<OrderedProduct, OrderedProductKey>
{
  List<OrderedProduct> findAllByOrderId(Long id);
}
