package com.pizza.backend.repositories;

import com.pizza.backend.entities.Order;
import com.pizza.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom
{
  List<Order> findAllByUser(User user);
  List<Order> findAllByDeliveryMan(User user);
}
