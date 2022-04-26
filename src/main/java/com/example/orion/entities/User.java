package com.example.orion.entities;

import com.example.orion.enums.Languages;
import com.example.orion.enums.RoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "users")
@NoArgsConstructor
@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleEnum role;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Phone phone;

    @NotNull
    private String config = "{{phone}} {{date}} {{number}}";

    @NotNull
    @Column(columnDefinition = "varchar(10) default 'TURKISH'")
    @Enumerated(EnumType.STRING)
    private Languages lang = Languages.TURKISH;

}
