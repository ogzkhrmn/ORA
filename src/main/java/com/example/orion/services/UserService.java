/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.services;

import com.example.orion.entities.User;
import com.example.orion.models.requests.AuthRequest;
import com.example.orion.models.requests.ConfigChangeRequest;

public interface UserService {
    User loginUser(AuthRequest authRequest);

    User findById(Long id);

    void changeConfig(ConfigChangeRequest request);
}
