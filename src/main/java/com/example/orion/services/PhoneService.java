/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.services;

import com.example.orion.models.requests.NewCallRequest;
import com.example.orion.models.requests.PhoneAddRequest;

public interface PhoneService {
    void addPhone(PhoneAddRequest request);

    void addCall(NewCallRequest request);

    void updateMissedCalls();
}
