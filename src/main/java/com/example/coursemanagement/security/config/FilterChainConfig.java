package com.example.coursemanagement.security.config;

import com.example.coursemanagement.security.jwt.AuthEntryPointJwt;
import com.example.coursemanagement.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class FilterChainConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(registry ->
                        registry
//                                .requestMatchers("/api/v1/auth/**").permitAll()
//                                .requestMatchers("api/v1/category-product/**").permitAll()
//                                .requestMatchers("api/v1/products/**").permitAll()
//                                .requestMatchers("api/v1/warehouses/**").permitAll()
//                                .requestMatchers("api/v1/product-types/**").permitAll()
//                                .requestMatchers("api/v1/customer-types/**").permitAll()
//                                .requestMatchers("api/v1/customers/**").permitAll()
//                                .requestMatchers("api/v1/warehouse-types/**").permitAll()
//                                .requestMatchers("api/v1/warehouse-zones/**").permitAll()
//                                .anyRequest().authenticated()
                                .anyRequest().permitAll()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(authEntryPointJwt)
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
