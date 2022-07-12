package com.atos.offer.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


public abstract class OfferException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    protected OfferException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
