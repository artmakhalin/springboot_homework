package com.makhalin.springboot_homework.config;

import com.makhalin.springboot_homework.service.CrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

import static com.makhalin.springboot_homework.entity.Role.ADMIN;
import static java.lang.reflect.Proxy.newProxyInstance;
import static org.springframework.security.web.csrf.CookieCsrfTokenRepository.withHttpOnlyFalse;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CrewService crewService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers(
                                "/login",
                                "/registration",
                                "/api/v1/registration",
                                "/api/v1/login",
                                "/v3/api-docs/**",
                                "/swagger-ui/**"
                        )
                        .permitAll()
                        .antMatchers(
                                "/api/v1/admin/**",
                                "/cities/**",
                                "/airports/**",
                                "/aircraft/**",
                                "/countries/**",
                                "/crew/**",
                                "/crewAircraft/**",
                                "/flights/**"
                        )
                        .hasAnyAuthority(ADMIN.getAuthority())
                        .anyRequest()
                        .authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/main")
                        .permitAll())
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/main")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService())))
                .csrf()
                .csrfTokenRepository(withHttpOnlyFalse());

        return http.build();
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            String email = userRequest.getIdToken()
                                      .getClaim("email");
            var userDetails = crewService.loadUserByUsername(email);
            var oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());

            var userDetailsMethods = Set.of(UserDetails.class
                    .getMethods());

            return (OidcUser) newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method)
                            ? method.invoke(userDetails, args)
                            : method.invoke(oidcUser, args));
        };
    }
}
