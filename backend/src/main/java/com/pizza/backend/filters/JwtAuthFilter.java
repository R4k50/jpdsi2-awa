package com.pizza.backend.filters;

import com.pizza.backend.providers.UserAuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter
{
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException
    {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }


        String[] authElements = header.split(" ");

        if (authElements.length != 2)
        {
            filterChain.doFilter(request, response);
            return;
        }


        String token = authElements[1];

        SecurityContextHolder.getContext().setAuthentication(
            userAuthenticationProvider.validateToken(token)
        );

        filterChain.doFilter(request, response);
    }
}
