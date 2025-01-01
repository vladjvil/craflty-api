package com.craftly.craftlyauth.config;

import com.craftly.craftlyauth.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.craftly.craftlyauth.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.craftly.craftlyauth.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.craftly.jwtmodule.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebFluxSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        customizer -> customizer
                                .requestMatchers(
                                        "/api/v1/auth/**",
                                        "/api/v1/2fa/login",
                                        "/oauth2/**"
                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(customizer -> {
                    customizer.authorizationEndpoint(endpointConfig -> {
                        endpointConfig.baseUri("/oauth2/authorize");
                        endpointConfig.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository);
                    });

                    customizer.redirectionEndpoint(endpointConfig ->
                            endpointConfig.baseUri("/oauth2/callback/*"));

                    customizer.userInfoEndpoint(endpointConfig ->
                            endpointConfig.userService(oAuth2UserService));

                    customizer.successHandler(oAuth2AuthenticationSuccessHandler);
                    customizer.failureHandler(oAuth2AuthenticationFailureHandler);
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}