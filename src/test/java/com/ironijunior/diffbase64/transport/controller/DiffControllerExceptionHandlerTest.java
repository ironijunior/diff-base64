package com.ironijunior.diffbase64.transport.controller;

import com.ironijunior.diffbase64.domain.enumerator.DiffSide;
import com.ironijunior.diffbase64.domain.exception.EntityNotFoundException;
import com.ironijunior.diffbase64.domain.exception.SideAlreadyFilledException;
import com.ironijunior.diffbase64.transport.dto.ErrorResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Locale;

public class DiffControllerExceptionHandlerTest {

    private DiffControllerExceptionHandler handler = new DiffControllerExceptionHandler();

    @Test
    public void entityNotFoundExceptionTest() {
        ErrorResponseDTO error = handler.handleEntityNotFoundException(
                new EntityNotFoundException("abc"), Locale.getDefault());

        ErrorResponseDTO expected = ErrorResponseDTO.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("There is no data with the identifier abc.")
                .build();

        Assertions.assertEquals(expected, error);
    }

    @Test
    public void sideAlreadyFilledExceptionTest() {
        ErrorResponseDTO error = handler.handleSideAlreadyFilledException(
                new SideAlreadyFilledException(DiffSide.RIGHT), Locale.getDefault());

        ErrorResponseDTO expected = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("The side RIGHT is already filled.")
                .build();

        Assertions.assertEquals(expected, error);
    }

    @Test
    public void unexpectedExceptionTest() {
        ErrorResponseDTO error = handler.handleUnexpectedException(
                new Exception("any message"), Locale.getDefault());

        ErrorResponseDTO expected = ErrorResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("any message")
                .build();

        Assertions.assertEquals(expected, error);
    }

}
