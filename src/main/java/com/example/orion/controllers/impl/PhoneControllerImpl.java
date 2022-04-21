/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.controllers.impl;

import com.example.orion.controllers.PhoneController;
import com.example.orion.models.requests.NewCallRequest;
import com.example.orion.models.requests.PhoneAddRequest;
import com.example.orion.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PhoneControllerImpl implements PhoneController {

    @Autowired
    private PhoneService phoneService;

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> addNewPhone(@Valid PhoneAddRequest request) {
        phoneService.addPhone(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> addCall(NewCallRequest request) {
        return null;
    }

}
