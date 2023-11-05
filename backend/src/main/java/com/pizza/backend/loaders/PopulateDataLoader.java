package com.pizza.backend.loaders;

import com.pizza.backend.dtos.RegisterDto;
import com.pizza.backend.entities.Role;
import com.pizza.backend.entities.User;
import com.pizza.backend.mappers.UserMapper;
import com.pizza.backend.repositories.RoleRepository;
import com.pizza.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PopulateDataLoader implements ApplicationRunner
{
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public void run(ApplicationArguments args)
  {
    populateRoles();
    populateUsers();
  }

  private void populateRoles()
  {
    createRole("ROLE_ADMIN");
    createRole("ROLE_DELIVERY");
  }

  private void createRole(String name)
  {
    Optional<Role> existingRole = roleRepository.findByName(name);

    if (!existingRole.isPresent())
    {
      roleRepository.save(Role.builder().name(name).build());
    }
  }

  private void populateUsers()
  {
    RegisterDto adminDto = RegisterDto.builder()
      .email("admin@mail.com")
      .name("admin")
      .surname("admin")
      .password("zaq1@WSX")
      .passwordConfirmation("zaq1@WSX")
    .build();

    RegisterDto deliveryDto = RegisterDto.builder()
      .email("delivery@mail.com")
      .name("delivery")
      .surname("delivery")
      .password("zaq1@WSX")
      .passwordConfirmation("zaq1@WSX")
    .build();

    RegisterDto userDto = RegisterDto.builder()
      .email("user@mail.com")
      .name("user")
      .surname("user")
      .password("zaq1@WSX")
      .passwordConfirmation("zaq1@WSX")
    .build();

    createUser(adminDto, "ROLE_ADMIN");
    createUser(deliveryDto, "ROLE_DELIVERY");
    createUser(userDto);
  }

  private void createUser(RegisterDto registerDto, String role)
  {
    Optional<User> existingUser = userRepository.findByEmail(registerDto.getEmail());

    if (!existingUser.isPresent())
    {
      User user = userMapper.registerToUser(registerDto);
      user.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPassword())));

      Role admin = roleRepository.getByName(role);

      Set<Role> roles = new HashSet<>();
      roles.add(admin);
      user.setRoles(roles);

      userRepository.save(user);
    }
  }

  private void createUser(RegisterDto registerDto)
  {
    Optional<User> existingUser = userRepository.findByEmail(registerDto.getEmail());

    if (!existingUser.isPresent())
    {
      User user = userMapper.registerToUser(registerDto);
      user.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPassword())));

      userRepository.save(user);
    }
  }
}