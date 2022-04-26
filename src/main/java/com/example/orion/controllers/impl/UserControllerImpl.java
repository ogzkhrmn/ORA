/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.controllers.impl;

import com.example.orion.constants.GeneralMessageConstants;
import com.example.orion.controllers.UserController;
import com.example.orion.core.exception.ConfigException;
import com.example.orion.core.i18n.Translator;
import com.example.orion.core.security.jwt.JWTService;
import com.example.orion.entities.User;
import com.example.orion.models.requests.AuthRequest;
import com.example.orion.models.requests.ConfigChangeRequest;
import com.example.orion.models.responses.UserResponse;
import com.example.orion.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Override
    public ResponseEntity<UserResponse> loginUser(AuthRequest request) {
        User user = userService.loginUser(request);
        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole().getRole());
        response.setToken(jwtService.createToken(user));
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<UserResponse> changeConfig(ConfigChangeRequest request) {
        if (!request.getConfig().contains("{{number}}")) {
            throw new ConfigException(HttpStatus.BAD_REQUEST, Translator.getMessage(GeneralMessageConstants.FIELD_NULL, "{{number}}"),
                    GeneralMessageConstants.NULL_ERROR);
        } else if (!request.getConfig().contains("{{phone}}")) {
            throw new ConfigException(HttpStatus.BAD_REQUEST, Translator.getMessage(GeneralMessageConstants.FIELD_NULL, "{{phone}}"),
                    GeneralMessageConstants.NULL_ERROR);
        } else if (!request.getConfig().contains("{{date}}")) {
            throw new ConfigException(HttpStatus.BAD_REQUEST, Translator.getMessage(GeneralMessageConstants.FIELD_NULL, "{{date}}"),
                    GeneralMessageConstants.NULL_ERROR);
        }
        userService.changeConfig(request);
        return ResponseEntity.noContent().build();
    }

}
