/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.repositories;

import com.example.orion.entities.MissedCall;
import org.springframework.data.repository.CrudRepository;

public interface MissedCallRepository extends CrudRepository<MissedCall, Long> {

}
