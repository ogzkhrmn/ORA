/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PhoneAddRequest {

    @Schema(description = "Phone of user", example = "5555555555", required = true)
    @Length(min = 10, max = 10)
    @NotBlank
    private String phone;
    @Schema(description = "User id", example = "2", required = true)
    @NotNull
    private Long userId;

}
