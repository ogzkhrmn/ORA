package com.example.orion.services.impl;

import com.example.orion.constants.GeneralMessageConstants;
import com.example.orion.core.exception.ConfigException;
import com.example.orion.core.i18n.Translator;
import com.example.orion.core.security.config.ConfigAuthProvider;
import com.example.orion.core.security.jwt.JWTService;
import com.example.orion.entities.User;
import com.example.orion.models.requests.AuthRequest;
import com.example.orion.models.responses.UserResponse;
import com.example.orion.repositories.UserRepository;
import com.example.orion.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfigAuthProvider authProvider;

    @Autowired
    private JWTService jwtService;

    @Override
    public UserResponse loginUser(AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.getUsername().toLowerCase())
                .orElseThrow(() -> new ConfigException(HttpStatus.FORBIDDEN, Translator.getMessage(GeneralMessageConstants.WRONG_INFO),
                        GeneralMessageConstants.WRONG_INFO_ERR));
        authProvider.authenticate(new UsernamePasswordAuthenticationToken(user, authRequest.getPassword(), jwtService.getUserRoles(user)));
        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole().getRole());
        response.setToken(jwtService.createToken(user));
        return response;
    }

    @Override
    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ConfigException(HttpStatus.BAD_REQUEST, Translator.getMessage(GeneralMessageConstants.USER_NOT_FOUND),
                        GeneralMessageConstants.USR_NOT_FOUND));
    }

}
