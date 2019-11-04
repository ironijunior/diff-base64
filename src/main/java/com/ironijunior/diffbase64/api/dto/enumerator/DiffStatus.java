package com.ironijunior.diffbase64.api.dto.enumerator;

public enum DiffStatus {

    EQUALS("Objects are equals"),
    DIFFERENT_SIZES("Objects are different in size"),
    DIFFERENT("Objects are different");

    DiffStatus(String value) {
    }
}
