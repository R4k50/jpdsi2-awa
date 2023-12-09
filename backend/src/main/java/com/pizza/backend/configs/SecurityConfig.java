package com.pizza.backend.configs;

import com.pizza.backend.entryPoints.UserAuthenticationEntryPoint;
import com.pizza.backend.filters.JwtAuthFilter;
import com.pizza.backend.providers.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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

                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/user").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/user/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/user/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/addRole/{userId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/removeRole/{userId}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/products").permitAll()
                .requestMatchers(HttpMethod.POST, "/product").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/product/{id}").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/product/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/product/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/orders").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/order").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/order/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/order/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/order/{id}").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/my-orders").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/assigned-orders").hasRole("DELIVERY")
                .requestMatchers(HttpMethod.GET, "/unassigned-orders").hasRole("DELIVERY")
                .requestMatchers(HttpMethod.PATCH, "/order/{id}/assign").hasRole("DELIVERY")
                .requestMatchers(HttpMethod.DELETE, "/assigned-order/{id}").hasRole("DELIVERY")

                .anyRequest().denyAll()
            );

        return http.build();
    }

    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults()
    {
        return new GrantedAuthorityDefaults("ROLE_");
    }

    @Bean
    static RoleHierarchy roleHierarchy()
    {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy(
            "ROLE_ADMIN > ROLE_DELIVERY\n" +
            "ROLE_DELIVERY > ROLE_USER"
        );

        return hierarchy;
    }

    @Bean
    static MethodSecurityExpressionHandler methodSecurityExpressionHandler()
    {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());

        return expressionHandler;
    }
}
