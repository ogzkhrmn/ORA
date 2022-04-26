/*
 * @author : Oguz Kahraman
 * @since : 25.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.models.requests;

import com.example.orion.enums.Languages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ConfigChangeRequest {

    @NotBlank
    @Schema(description = "User config values", example = "{{phone}} {{date}} {{number}}", required = true)
    private String config;

    @NotNull
    @Schema(description = "User config language", example = "TURKISH", required = true)
    private Languages lang;

}
