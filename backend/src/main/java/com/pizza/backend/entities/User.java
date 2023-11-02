package com.pizza.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 50)
    private String name;

    @Column(name = "surname", nullable = false)
    @Size(min = 2, max = 50)
    private String surname;

    @Column(name = "email", nullable = false, unique = true)
    @Size(min = 2, max = 50)
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min = 8, max = 100)
    private String password;
}
