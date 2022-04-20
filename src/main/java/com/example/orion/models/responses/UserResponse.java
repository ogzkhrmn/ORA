/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.models.responses;

import lombok.Data;

@Data
public class UserResponse {

    private String username;
    private String role;
    private String token;

}
