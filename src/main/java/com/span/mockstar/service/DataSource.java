package com.span.mockstar.service;

public enum DataSource {
    CACHE("cache"),
    REMOTE("remote"),
    CONFIG("config");
    public final String label;

    private DataSource(String label) {
        this.label = label;
    }
}
