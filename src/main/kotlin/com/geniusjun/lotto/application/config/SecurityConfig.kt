package com.geniusjun.lotto.application.config

import com.geniusjun.lotto.application.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/api/auth/**").permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }
        return http.build()
    }
}
