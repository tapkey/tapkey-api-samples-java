package io.tapkey.developers;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SampleSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/public-key").permitAll()
                .pathMatchers(HttpMethod.POST, "/user").permitAll()
                .anyExchange().authenticated()
                .and().httpBasic()
                .and().build();
    }
}
