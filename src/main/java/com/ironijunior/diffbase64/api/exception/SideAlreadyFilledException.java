package com.ironijunior.diffbase64.api.exception;

import com.ironijunior.diffbase64.domain.enumerator.DiffSide;

public class SideAlreadyFilledException extends RuntimeException {

    private static final String MSG = "The side %s is already filled.";

    public SideAlreadyFilledException(DiffSide side) {
        super(String.format(MSG, side.name()));
    }
}
