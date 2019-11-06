package com.ironijunior.diffbase64.transport.controller;

import com.ironijunior.diffbase64.domain.exception.EntityNotFoundException;
import com.ironijunior.diffbase64.domain.exception.SideAlreadyFilledException;
import com.ironijunior.diffbase64.transport.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;

@ControllerAdvice
public class DiffControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDTO handleEntityNotFoundException(EntityNotFoundException ex, Locale locale) {
        return ErrorResponseDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(SideAlreadyFilledException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDTO handleSideAlreadyFilledException(SideAlreadyFilledException ex, Locale locale) {
        return ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
    }

}
