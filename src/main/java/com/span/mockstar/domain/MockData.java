package com.span.mockstar.domain;

import com.span.mockstar.service.DataSource;

public record MockData(String data, DataSource source, String hash) {
}
