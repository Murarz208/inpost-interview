package com.maciejmurawski.inpost.policies.discount;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
class DiscountPolicyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DiscountPolicyNotFoundException.class)
    ErrorResponse handleDiscountPolicyNotFoundException(DiscountPolicyNotFoundException e) {
        return ErrorResponse.builder(e, HttpStatus.NOT_FOUND, e.getMessage())
                .title("Discount policy not found")
                .property("timestamp", Instant.now())
                .build();
    }

    @ExceptionHandler(DiscountPolicyValidationException.class)
    ErrorResponse handleDiscountPolicyValidationException(DiscountPolicyValidationException e) {
        return ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
                .title("Discount policy validation failed")
                .property("timestamp", Instant.now())
                .build();
    }

}