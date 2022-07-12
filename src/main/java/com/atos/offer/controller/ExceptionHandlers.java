package com.atos.offer.controller;

import com.atos.offer.exception.OfferException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
@Order
public class ExceptionHandlers {

    @ExceptionHandler(OfferException.class)
    protected final ResponseEntity<Error> handleRuntimeException(OfferException ex) {
        var errorResponse = new Error(ex.getStatus(), ex.getMessage(), OffsetDateTime.now().toString());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected final ResponseEntity<Error> handleRuntimeException(MissingServletRequestParameterException ex) {
        var errorResponse = new Error(HttpStatus.BAD_REQUEST, ex.getMessage(), OffsetDateTime.now().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected final ResponseEntity<Error> handleRuntimeException(MethodArgumentNotValidException ex) {
        var errorResponse = new Error(HttpStatus.BAD_REQUEST, "Validation failed for argument " + ex.getObjectName(), OffsetDateTime.now().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Getter
    @AllArgsConstructor
    public static class Error {
        private HttpStatus status;
        private String message;
        private String timestamp;
    }
}
