//package com.kombucha.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.List;
//
//@Configuration
//public class WebSecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf().disable()
//                .cors().disable()
//                .authorizeHttpRequests(authz->authz.anyRequest().permitAll())
//                .build();
//    }
//}
