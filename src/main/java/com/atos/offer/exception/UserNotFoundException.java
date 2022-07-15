package com.atos.offer.exception;

import com.atos.offer.model.UserId;
import org.springframework.http.HttpStatus;

/**
 * UserNotFoundException occurs when trying to fetch a user that oes not exist.
 */
public class UserNotFoundException extends OfferException {
    public UserNotFoundException(UserId userId) {
        super("User: " + userId.toString() + " does not exist.", HttpStatus.NOT_FOUND);
    }

}