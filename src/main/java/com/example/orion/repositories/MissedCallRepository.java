/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.repositories;

import com.example.orion.entities.MissedCall;
import com.example.orion.entities.Phone;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface MissedCallRepository extends CrudRepository<MissedCall, Long> {

    @Query(value = "SELECT mc.userId, mc.phone, count(mc), max(mc.date), min(mc.received)" +
            " FROM MissedCall mc WHERE mc.userId = :userId and mc.read = false" +
            " group by mc.userId, mc.phone.phoneNumber")
    List<Object[]> findMissedCalls(Long userId);

    @Modifying
    @Query(value = "UPDATE MissedCall mc SET mc.read = true WHERE mc.userId = :userId and mc.read = false")
    void updateMissedCalls(Long userId);

    @Modifying
    @Query(value = "UPDATE MissedCall mc SET mc.received = true WHERE mc.phone = :phone and mc.received = false")
    @Transactional
    void updateRecieved(Phone phone);

}
