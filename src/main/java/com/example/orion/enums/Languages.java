/*
 * @author : Oguz Kahraman
 * @since : 26.04.2022
 *
 * Copyright - Orion
 **/
package com.example.orion.enums;

public enum Languages {

    ENGLISH("en"),
    TURKISH("tr");

    private final String language;

    Languages(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return this.language;
    }

}
