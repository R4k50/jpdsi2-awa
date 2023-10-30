package com.pizza.backend.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pizza.backend.dtos.UserDto;
import com.pizza.backend.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider
{
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final UserService userService;

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

        UserDto user = null;

        if (!isTokenExpired(verifier, token))
        {
            DecodedJWT decoded = verifier.verify(token);
            user = userService.findByEmail(decoded.getSubject());
        }

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
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
}
