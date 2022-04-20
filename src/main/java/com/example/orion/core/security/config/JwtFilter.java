package com.example.orion.core.security.config;

import com.example.orion.core.exception.ConfigException;
import com.example.orion.core.security.jwt.JWTService;
import com.example.orion.core.security.jwt.JWTUtil;
import com.example.orion.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final JWTUtil util;

    public JwtFilter(JWTService jwtService, JWTUtil util) {
        this.jwtService = jwtService;
        this.util = util;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(util.getHeaderString());
        if (token != null) {
            try {
                if (jwtService.isTokenExpired(token)) {
                    throw new ConfigException(HttpStatus.UNAUTHORIZED, "JWT token is expired", "JWT_TOKEN_EXPIRED");
                }
            } catch (Exception e) {
                throw new ConfigException(HttpStatus.UNAUTHORIZED, "JWT token is invalid", "JWT_TOKEN_INVALID");
            }
            User user = jwtService.getUserFromToken(token);
            UsernamePasswordAuthenticationToken authentication = jwtService.getAuthenticationToken(user);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
