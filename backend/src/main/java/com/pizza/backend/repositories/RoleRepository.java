package com.pizza.backend.repositories;

import com.pizza.backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long>
{
  Optional<Role> findByName(String name);
  Role getByName(String name);
}
