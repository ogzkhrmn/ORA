/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.websocket;

import com.example.orion.core.exception.ConfigException;
import com.example.orion.core.security.jwt.JWTService;
import com.example.orion.core.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SecurityUtil {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private JWTUtil util;


    public boolean checksUserToken(String token, Map<String, Object> map) {
        if (token == null || !token.contains(util.getTokenPrefix())) {
            throw new ConfigException(HttpStatus.UNAUTHORIZED, "JWT token is invalid", "JWT_TOKEN_INVALID");
        }
        if (jwtService.isTokenExpired(token)) {
            throw new ConfigException(HttpStatus.UNAUTHORIZED, "JWT token is expired", "JWT_TOKEN_EXPIRED");
        }
        map.put("user", jwtService.getUserFromToken(token));
        return true;
    }

}
