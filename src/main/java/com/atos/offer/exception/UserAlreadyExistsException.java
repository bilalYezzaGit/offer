package com.atos.offer.exception;

import com.atos.offer.model.UserId;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends OfferException {
    public UserAlreadyExistsException(UserId userId) {
        super("user : " + userId.toString() + " already exists.", HttpStatus.BAD_REQUEST);
    }

}