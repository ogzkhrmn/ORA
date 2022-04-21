/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.repositories;

import com.example.orion.entities.Phone;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PhoneRepository extends CrudRepository<Phone, Long> {

    Optional<Phone> findByPhoneNumber(String phoneNumber);

}
