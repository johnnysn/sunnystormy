package com.uriel.sunnystormy.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleStatusException(ResponseStatusException ex)
    {
        LOGGER.warn("Status exception caught", ex);

        return ResponseEntity.status(ex.getStatusCode()).body(new ExceptionResponse(
                ex.getStatusCode().value(), ex.getMessage()
        ));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ExceptionResponse handleInternalErrorException(RuntimeException ex)
    {
        LOGGER.error("Runtime exception caught", ex);

        return new ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error"
        );
    }

}
