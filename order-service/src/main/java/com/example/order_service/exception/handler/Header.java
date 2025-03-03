package com.example.order_service.exception.handler;

public enum Header {
    API_ERROR("API ERROR"),
    ALREADY_EXIST("ALREADY EXIST"),
    NOT_FOUND("NOT EXIST"),
    VALIDATION_ERROR("VALIDATION ERROR"),
    DATABASE_ERROR("DATABASE ERROR"),
    PROCESS_ERROR("PROCESS ERROR"),
    AUTH_ERROR("AUTH ERROR");

    private final String name;

    Header(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}