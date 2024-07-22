package com.span.mockstar.controller;

import com.span.mockstar.config.filter.CachedBodyServletInputStream;
import com.span.mockstar.domain.MockData;
import com.span.mockstar.service.MockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.span.mockstar.config.security.ApplicationConfiguration.*;


@RequestMapping("/mock/**")
@RestController
public class MockController {

    private static final Logger logger = LoggerFactory.getLogger(CachedBodyServletInputStream.class);
    private final MockService mockService;

    public MockController(MockService mockService) {
        this.mockService = mockService;
    }

    @GetMapping
    public ResponseEntity<String> fetchDataGet(@RequestAttribute(name = REQUEST_HASH) String hash,
                                               @RequestAttribute(name = CONTENT_TARGET) String target,
                                               @RequestAttribute(name = CONTENT_TARGET_PATH) String targetPath,
                                               @RequestAttribute(name = CONTENT_EXPIRES_IN) long expiresIn,
                                               @RequestBody(required = false) String requestBody,
                                               @RequestHeader HttpHeaders headers) {
        logger.info("Received Get request for {}{}", target, targetPath);
        updateRequestHeaders(headers, target, targetPath);
        MockData response = mockService.fetchData(hash, RequestMethod.GET, requestBody, headers, expiresIn);
        return ResponseEntity.ok().headers(createResponseHeaders(response)).body(response.data());
    }

    @PostMapping
    public ResponseEntity<String> fetchDataPost(@RequestAttribute(name = REQUEST_HASH) String hash,
                                                @RequestAttribute(name = CONTENT_TARGET) String target,
                                                @RequestAttribute(name = CONTENT_TARGET_PATH) String targetPath,
                                                @RequestAttribute(name = CONTENT_EXPIRES_IN) long expiresIn,
                                                @RequestBody(required = false) String requestBody,
                                                @RequestHeader HttpHeaders headers) {
        logger.info("Received Post request for {}{}", target, targetPath);
        try {
            updateRequestHeaders(headers, target, targetPath);
            MockData response = mockService.fetchData(hash, RequestMethod.POST, requestBody, headers, expiresIn);
            return ResponseEntity.ok().headers(createResponseHeaders(response)).body(response.data());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void updateRequestHeaders(HttpHeaders headers, String target, String targetPath) {
        headers.add(CONTENT_TARGET, target);
        headers.add(CONTENT_TARGET_PATH, targetPath);
    }

    private HttpHeaders createResponseHeaders(MockData response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(DATA_SOURCE, response.source().label);
        headers.set(REQUEST_HASH, response.hash());
        return headers;
    }
}