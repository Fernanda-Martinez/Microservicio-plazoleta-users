package com.pragma.powerup.infrastructure.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class ConfiguracionSeguridad {

    @Bean
    public SecurityFilterChain filtroPorDefecto(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors().and().csrf().disable().authorizeRequests().antMatchers(
                "/swagger-ui/**", "/swagger-iu.html", "/v3/api-docs/**", "/api/v1/usuario/**",
                "/api/v1/roles/**"
        ).permitAll().and().formLogin().disable();

        return httpSecurity.build();
    }
}
