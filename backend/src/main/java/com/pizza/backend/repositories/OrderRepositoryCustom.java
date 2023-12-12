package com.pizza.backend.repositories;

import com.pizza.backend.dtos.orders.FullOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom
{
  Page<FullOrderDto> findAll(String searchParam, String searchValue, Pageable pageable);
}
