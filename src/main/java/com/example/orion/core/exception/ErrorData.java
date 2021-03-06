package com.example.orion.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorData {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}