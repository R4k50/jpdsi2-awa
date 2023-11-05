package com.pizza.backend.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pizza.backend.dtos.UserDto;
import com.pizza.backend.entities.Role;
import com.pizza.backend.entities.User;
import com.pizza.backend.mappers.UserMapper;
import com.pizza.backend.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider
{
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final UserService userService;
    private final UserMapper userMapper;

    @PostConstruct
    protected void init()
    {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login)
    {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
            .withSubject(login)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .sign(algorithm);
    }

    public Authentication validateToken(String token)
    {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        User user = null;
        List<Role> roles = new ArrayList<>();

        if (!isTokenExpired(verifier, token))
        {
            DecodedJWT decoded = verifier.verify(token);
            user = userService.findByEmail(decoded.getSubject());
        }

        if (user != null)
        {
            roles.addAll(user.getRoles());
        }

        return new UsernamePasswordAuthenticationToken(user, null, roles);
    }

    public boolean isTokenExpired(JWTVerifier verifier, String token)
    {
        try
        {
            DecodedJWT decoded = verifier.verify(token);
        }
        catch (JWTVerificationException e)
        {
            return true;
        }

        return false;
    }

    public UserDto getAuthenticatedUserDto()
    {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userMapper.toUserDto(user);
    }
}
