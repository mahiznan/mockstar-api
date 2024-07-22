package com.span.mockstar.service;

import com.span.mockstar.domain.MockData;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Instant;

//TODO: Change this to functional interface
public interface MockService {
    MockData fetchData(String hash, RequestMethod requestMethod, String requestBody, HttpHeaders headers, long expiresIn);

    void deleteCacheBefore(Instant time);
}
