package com.atos.offer.exception;

import com.atos.offer.model.UserId;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends OfferException {
    public UserNotFoundException(UserId userId) {
        super("User: " + userId.toString() + " does not exist.", HttpStatus.NOT_FOUND);
    }

}