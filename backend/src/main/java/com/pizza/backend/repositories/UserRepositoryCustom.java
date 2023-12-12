package com.pizza.backend.repositories;

import com.pizza.backend.dtos.users.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom
{
  Page<UserDto> findAll(String searchParam, String searchValue, Pageable pageable);
}
