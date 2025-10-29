package vn.edu.fpt.elios_user_service.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final ProxyAuthFilter proxyAuthFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(ProxyAuthFilter proxyAuthFilter,
                          CustomAccessDeniedHandler accessDeniedHandler) {
        this.proxyAuthFilter = proxyAuthFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(proxyAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        // Example fine-grained rules
                        .requestMatchers("/admin/**").hasAuthority(RoleConstants.ADMIN)
                        .requestMatchers("/users/**").hasAnyAuthority(RoleConstants.ADMIN, RoleConstants.USER)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }
}
