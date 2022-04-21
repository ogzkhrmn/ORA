/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.controllers;

import com.example.orion.core.exception.ErrorData;
import com.example.orion.models.requests.NewCallRequest;
import com.example.orion.models.requests.PhoneAddRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Phone operations", description = "This endpoint performs phone operations")
public interface PhoneController {

    @Operation(summary = "Add new Phone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Added"),
            @ApiResponse(responseCode = "400", description = "Phone add error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorData.class))})})
    @PostMapping(value = "/phones")
    ResponseEntity<Void> addNewPhone(@RequestBody PhoneAddRequest request);

    @Operation(summary = "New Phone Call")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Called"),
            @ApiResponse(responseCode = "400", description = "Phone call error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorData.class))})})
    @PostMapping(value = "/phones/call")
    ResponseEntity<Void> addCall(@RequestBody NewCallRequest request);

}
