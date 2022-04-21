/*
 * @author : Oguz Kahraman
 * @since : 20.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "missed_call")
@NoArgsConstructor
@Entity
@Getter
@Setter
public class MissedCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    private LocalDateTime localDateTime = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_id")
    private List<Phone> phones = new ArrayList<>();

    private boolean received;

}
