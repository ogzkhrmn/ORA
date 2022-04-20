package com.example.orion.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ConfigException extends ResponseStatusException {

    public ConfigException(HttpStatus status, String message, String error) {
        super(status, message, new Exception(error));
    }

}