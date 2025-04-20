package com.example.SpringBoot_Backend_Sercurity.Security_Config;

import com.example.SpringBoot_Backend_Sercurity.Security_Config.CustomuserDetailService;
import com.example.SpringBoot_Backend_Sercurity.Security_Config.JWTFilter;
import com.example.SpringBoot_Backend_Sercurity.Security_Config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomuserDetailService userDetailsService;

    @Autowired
    private JWTFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Cập nhật các phương thức cấu hình bảo mật
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz ->
                        authz
                                .requestMatchers("/auth/**", "/oauth2/**").permitAll()  // Thay thế authorizeHttpRequests()
                                .anyRequest().authenticated()  // Thay thế authorizeHttpRequests()
                )
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/auth/oauth2/success", true)  // Cập nhật oauth2Login()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Cập nhật sessionManagement()
                );

        // Thêm filter JWT trước UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
