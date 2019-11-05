package com.ironijunior.diffbase64.api.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final String MSG = "There is no data with the identifier %s";

    public EntityNotFoundException(String id) {
        super(String.format(MSG, id));
    }

}
