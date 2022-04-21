/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.services;

import com.example.orion.entities.User;
import com.example.orion.models.requests.AuthRequest;
import com.example.orion.models.responses.UserResponse;

public interface UserService {
    UserResponse loginUser(AuthRequest authRequest);

    User findById(Long id);
}
