package com.span.mockstar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("client")
public class Config {
    private final Map<String, ClientDetails> list;

    public Config(Map<String, ClientDetails> list) {
        this.list = list;
    }

    public Map<String, ClientDetails> getList() {
        return list;
    }

    public record ClientDetails(String name, String context, long expiresInSec, List<String> header) {

    }
}
