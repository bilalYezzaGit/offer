package com.atos.offer.controller;

import com.atos.offer.exception.OfferException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

/**
 * Handler for exceptions thrown by controller
 */
@ControllerAdvice
@Order
public class ExceptionHandlers {

    /**
     * Handles All exceptions defined in offer project
     *
     * @param ex offer exception
     * @return Response entity with error message
     */
    @ExceptionHandler(OfferException.class)
    protected final ResponseEntity<Error> handleRuntimeException(OfferException ex) {
        var errorResponse = new Error(ex.getStatus(), ex.getMessage(), OffsetDateTime.now().toString());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    /**
     * Handles MissingServletRequestParameterException exception
     *
     * @param ex exception
     * @return Response entity with error message
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected final ResponseEntity<Error> handleRuntimeException(MissingServletRequestParameterException ex) {
        var errorResponse = new Error(HttpStatus.BAD_REQUEST, ex.getMessage(), OffsetDateTime.now().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles All exceptions related to validation
     *
     * @param ex exception
     * @return Response entity with error message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected final ResponseEntity<Error> handleRuntimeException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        var errorResponse = new Error(HttpStatus.BAD_REQUEST, errorMessage, OffsetDateTime.now().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error object
     */
    @Getter
    @AllArgsConstructor
    public static class Error {
        private HttpStatus status;
        private String message;
        private String timestamp;
    }
}
