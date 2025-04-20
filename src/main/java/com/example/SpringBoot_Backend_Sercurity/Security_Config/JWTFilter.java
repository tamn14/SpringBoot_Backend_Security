package com.example.SpringBoot_Backend_Sercurity.Security_Config;

import com.example.SpringBoot_Backend_Sercurity.DTO.Request.InstrospectRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final CustomuserDetailService userDetailsService;

    public JWTFilter(JwtTokenProvider tokenProvider, CustomuserDetailService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            InstrospectRequest instrospectRequest = new InstrospectRequest(token);

            if (tokenProvider.introspect(instrospectRequest).isValid()) {
                String username = tokenProvider.getUserNameByToken(token);
                var userDetails = userDetailsService.loadUserByUsername(username);

                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
