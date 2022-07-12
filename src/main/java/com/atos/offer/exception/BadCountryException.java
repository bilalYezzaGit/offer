package com.atos.offer.exception;

import org.springframework.http.HttpStatus;

public class BadCountryException extends OfferException {
    public BadCountryException(String country) {
        super("Cannot register user with Country " + country + ".", HttpStatus.BAD_REQUEST);
    }

}