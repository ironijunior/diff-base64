package com.ironijunior.diffbase64.domain.enumerator;

public enum DiffStatus {

    NO_DIFF("Objects are not checked yet"),
    EQUALS("Objects are equals"),
    DIFFERENT_SIZES("Objects are different in size"),
    DIFFERENT("Objects are different");

    private String text;

    DiffStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
