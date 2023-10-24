package com.pizza.backend.services;

import com.pizza.backend.dtos.LoginDto;
import com.pizza.backend.dtos.RegisterDto;
import com.pizza.backend.dtos.UserDto;
import com.pizza.backend.entites.User;
import com.pizza.backend.exceptions.AppException;
import com.pizza.backend.mappers.UserMapper;
import com.pizza.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserRepository userRepository;
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

    public UserDto findByEmail(String login)
    {
        User user = userRepository.findByEmail(login)
            .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        return userMapper.toUserDto(user);
    }
}
