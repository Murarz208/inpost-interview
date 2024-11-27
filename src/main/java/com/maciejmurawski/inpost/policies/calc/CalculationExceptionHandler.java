package com.maciejmurawski.inpost.policies.calc;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
class CalculationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DiscountCalculationException.class)
    ErrorResponse handleDiscountCalculationException(DiscountCalculationException e) {
        return ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage())
                .title("Error when calculating the discount")
                .property("timestamp", Instant.now())
                .build();
    }
}