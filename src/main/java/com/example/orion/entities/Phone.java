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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "phones")
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String phoneNumber;

}
