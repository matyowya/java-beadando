package org.example.javabeadando.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/register", "/adatbazis", "/kapcsolat", "/error", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
                .requestMatchers("/user", "/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                // Belépés után: admin -> /admin, mindenki más -> /user
                .successHandler((request, response, authentication) -> {
                    boolean isAdmin = authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .anyMatch("ROLE_ADMIN"::equals);
                    response.sendRedirect(request.getContextPath() + (isAdmin ? "/admin" : "/user"));
                })
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    String path = request.getRequestURI().substring(request.getContextPath().length());
                    String target = path.startsWith("/admin") ? "/" : "/login";
                    response.sendRedirect(request.getContextPath() + target);
                })
                .accessDeniedHandler((request, response, accessDeniedException) ->
                        response.sendRedirect(request.getContextPath() + "/"))
            );
        return http.build();
    }
}
