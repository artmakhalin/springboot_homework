package com.makhalin.springboot_homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.makhalin.springboot_homework.entity.Role.ADMIN;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers(
                                "/login",
                                "/crew/registration",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/crew"
                        )
                        .permitAll()
                        .antMatchers(
                                "/cities/**",
                                "/countries/**"
                        )
                        .hasAnyAuthority(ADMIN.getAuthority())
                        .anyRequest()
                        .authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .formLogin(
                        login -> login
                                .loginPage("/login")
                                .defaultSuccessUrl("/main")
                                .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
