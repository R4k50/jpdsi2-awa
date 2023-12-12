package com.pizza.backend.repositories;

import com.pizza.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom
{
    Optional<User> findByEmail(String email);
}
