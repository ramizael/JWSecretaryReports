package com.andresalcantar.jwsecretaryreports.enums;

/**
 * Created by andres.alcantar on 23/12/2016.
 */

public enum PublisherType {
    PUBLISHER("P"),
    AUXILIAR("A"),
    REGULAR("R"),
    SPECIAL("S");

    private String publisherString;

    PublisherType(String p) {
        this.publisherString = p;
    }
}
