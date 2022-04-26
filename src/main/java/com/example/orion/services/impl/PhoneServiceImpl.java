/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.services.impl;

import com.example.orion.constants.GeneralMessageConstants;
import com.example.orion.core.exception.ConfigException;
import com.example.orion.core.i18n.Translator;
import com.example.orion.core.security.jwt.JWTService;
import com.example.orion.entities.MissedCall;
import com.example.orion.entities.Phone;
import com.example.orion.entities.User;
import com.example.orion.models.requests.NewCallRequest;
import com.example.orion.models.requests.PhoneAddRequest;
import com.example.orion.repositories.MissedCallRepository;
import com.example.orion.repositories.PhoneRepository;
import com.example.orion.services.PhoneService;
import com.example.orion.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private MissedCallRepository missedCallRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @Override
    public void addPhone(PhoneAddRequest request) {
        User user = userService.findById(request.getUserId());
        Phone phone = new Phone();
        phone.setPhoneNumber(request.getPhone());
        phone.setId(request.getUserId());
        phone.setUser(user);
        phoneRepository.save(phone);
    }

    @Override
    public void addCall(NewCallRequest request) {
        User user = jwtService.getLoggedUser();
        if (user.getPhone() == null) {
            throw new ConfigException(HttpStatus.BAD_REQUEST,
                    Translator.getMessage(GeneralMessageConstants.ADD_PHONE), GeneralMessageConstants.NO_PHONE);
        }
        if (user.getPhone().getPhoneNumber().equals(request.getTo())) {
            throw new ConfigException(HttpStatus.BAD_REQUEST,
                    Translator.getMessage(GeneralMessageConstants.USER_NUMBER), GeneralMessageConstants.NUMBER_RRROR);
        }
        Phone to = phoneRepository.findByPhoneNumber(request.getTo())
                .orElseThrow(() -> new ConfigException(HttpStatus.BAD_REQUEST,
                        Translator.getMessage(GeneralMessageConstants.PHONE_NOT_FOUND, request.getTo()),
                        GeneralMessageConstants.USR_NOT_FOUND));
        MissedCall missedCall = new MissedCall();
        missedCall.setUserId(to.getUser().getId());
        missedCall.setPhone(user.getPhone());
        missedCallRepository.save(missedCall);
    }

    @Override
    @Transactional
    public void updateMissedCalls() {
        User user = jwtService.getLoggedUser();
        missedCallRepository.updateMissedCalls(user.getId());
    }

}
