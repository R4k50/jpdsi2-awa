package com.pizza.backend.services;

import com.pizza.backend.dtos.LoginDto;
import com.pizza.backend.dtos.RegisterDto;
import com.pizza.backend.dtos.UserDto;
import com.pizza.backend.entities.Role;
import com.pizza.backend.entities.User;
import com.pizza.backend.exceptions.AppException;
import com.pizza.backend.mappers.UserMapper;
import com.pizza.backend.repositories.RoleRepository;
import com.pizza.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto login(LoginDto loginDto)
    {
        User user = userRepository.findByEmail(loginDto.getEmail())
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
            throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);

        return userMapper.toUserDto(user);
    }

    public UserDto register(RegisterDto registerDto)
    {
        Optional<User> existingUser = userRepository.findByEmail(registerDto.getEmail());

        if (existingUser.isPresent())
            throw new AppException("Email already taken", HttpStatus.BAD_REQUEST);

        if (!registerDto.getPassword().equals(registerDto.getPasswordConfirmation()))
            throw new AppException("Passwords do not match", HttpStatus.BAD_REQUEST);

        User user = userMapper.registerToUser(registerDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(registerDto.getPassword())));

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }

    public User findByEmail(String email)
    {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        return user;
    }

    public void addRole(Long id, String roleName)
    {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        Role role = roleRepository.findByName(roleName)
            .orElseThrow(() -> new AppException("Unknown role", HttpStatus.NOT_FOUND));

        Set<Role> roles = user.getRoles();

        if (roles.contains(role))
        {
            throw new AppException("This user already have the given role", HttpStatus.BAD_REQUEST);
        }

        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void removeRole(Long id, String roleName)
    {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        Role role = roleRepository.findByName(roleName)
            .orElseThrow(() -> new AppException("Unknown role", HttpStatus.NOT_FOUND));

        Set<Role> roles = user.getRoles();

        if (!roles.contains(role))
        {
            throw new AppException("This user do not have the given role", HttpStatus.BAD_REQUEST);
        }

        roles.remove(role);
        user.setRoles(roles);
        userRepository.save(user);
    }
}
