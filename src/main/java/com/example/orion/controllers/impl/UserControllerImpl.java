/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.controllers.impl;

import com.example.orion.controllers.UserController;
import com.example.orion.models.requests.AuthRequest;
import com.example.orion.models.responses.UserResponse;
import com.example.orion.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<UserResponse> loginUser(AuthRequest request) {
        return ResponseEntity.ok(userService.loginUser(request));
    }

}
