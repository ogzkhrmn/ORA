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
import com.example.orion.entities.MissedCall;
import com.example.orion.entities.Phone;
import com.example.orion.models.requests.NewCallRequest;
import com.example.orion.models.requests.PhoneAddRequest;
import com.example.orion.repositories.MissedCallRepository;
import com.example.orion.repositories.PhoneRepository;
import com.example.orion.services.PhoneService;
import com.example.orion.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PhoneServiceImpl implements PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private MissedCallRepository missedCallRepository;

    @Autowired
    private UserService userService;

    @Override
    public void addPhone(PhoneAddRequest request) {
        userService.findById(request.getUserId());
        Phone phone = new Phone();
        phone.setPhoneNumber(request.getPhone());
        phone.setId(request.getUserId());
        phoneRepository.save(phone);
    }

//    @Override
//    public void addCall(NewCallRequest request) {
//       Phone from = phoneRepository.findByPhoneNumber(request.getFrom())
//               .orElseThrow(() -> new ConfigException(HttpStatus.BAD_REQUEST,
//                               Translator.getMessage(GeneralMessageConstants.PHONE_NOT_FOUND, request.getFrom()),
//                               GeneralMessageConstants.USR_NOT_FOUND));
//        Phone to = phoneRepository.findByPhoneNumber(request.getFrom())
//                .orElseThrow(() -> new ConfigException(HttpStatus.BAD_REQUEST,
//                        Translator.getMessage(GeneralMessageConstants.PHONE_NOT_FOUND, request.getTo()),
//                        GeneralMessageConstants.USR_NOT_FOUND));
//        MissedCall missedCall = new MissedCall();
//    }

}
