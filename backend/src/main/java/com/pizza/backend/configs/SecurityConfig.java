package com.pizza.backend.configs;

import com.pizza.backend.entryPoints.UserAuthenticationEntryPoint;
import com.pizza.backend.filters.JwtAuthFilter;
import com.pizza.backend.providers.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
            .exceptionHandling(customizer -> customizer.authenticationEntryPoint(userAuthenticationEntryPoint))
            .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers(HttpMethod.POST, "/login", "/register").anonymous()

                .requestMatchers(HttpMethod.GET, "/products").permitAll()
                .requestMatchers(HttpMethod.POST, "/product").authenticated()
                .requestMatchers(HttpMethod.GET, "/product/{id}").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/product/{id}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/product/{id}").authenticated()

                .requestMatchers(HttpMethod.GET, "/orders").authenticated()
                .requestMatchers(HttpMethod.POST, "/order").authenticated()
                .requestMatchers(HttpMethod.GET, "/order/{id}").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/order/{id}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/order/{id}").authenticated()

                .anyRequest().denyAll()
            );

        return http.build();
    }
}
