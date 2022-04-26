/*
 * @author : Oguz Kahraman
 * @since : 21.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class NewCallRequest {

    @Schema(description = "Destination phone", example = "5554443322", required = true)
    @Length(min = 10, max = 10)
    @NotBlank
    private String to;

}
