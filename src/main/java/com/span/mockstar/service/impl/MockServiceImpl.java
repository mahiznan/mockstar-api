package com.span.mockstar.service.impl;

import com.span.mockstar.client.GatewayClient;
import com.span.mockstar.domain.MockData;
import com.span.mockstar.entity.CacheData;
import com.span.mockstar.repository.CacheDataRepository;
import com.span.mockstar.service.DataSource;
import com.span.mockstar.service.MockService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.span.mockstar.config.security.ApplicationConfiguration.CONTENT_TARGET;

@Service
public class MockServiceImpl implements MockService {

    private final CacheDataRepository repository;
    private final GatewayClient client;
    private static final Logger log = LoggerFactory.getLogger(MockServiceImpl.class);

    public MockServiceImpl(CacheDataRepository cacheDataRepository, GatewayClient client) {
        this.repository = cacheDataRepository;
        this.client = client;
    }

    @Override
    public MockData fetchData(String hash, RequestMethod requestMethod, String requestBody, HttpHeaders headers, long expiresIn) {
        Optional<CacheData> cacheData = repository.findByHashAndExpiresAtAfter(hash, Instant.now());
        if (cacheData.isPresent()) {
            log.info("Cache data presents in database");
            return new MockData(cacheData.get().getValue(), DataSource.CACHE, hash);
        } else {
            log.info("Fetching data from remote system");
            String response = switch (requestMethod) {
                case GET -> {
                    if (requestBody != null && !requestBody.isEmpty()) {
                        yield client.triggerGETWithBody(requestBody, headers);
                    } else {
                        yield client.triggerGET(headers);
                    }
                }
                case POST -> {
                    if (requestBody != null && !requestBody.isEmpty()) {
                        yield client.triggerPOST(requestBody, headers);
                    } else {
                        yield client.triggerPOSTNoBody(headers);
                    }
                }

                case PUT -> {
                    if (requestBody != null && !requestBody.isEmpty()) {
                        yield client.triggerPUT(requestBody, headers);
                    } else {
                        yield client.triggerPUTNoBody(headers);
                    }
                }
                case DELETE -> {
                    if (requestBody != null && !requestBody.isEmpty()) {
                        yield client.triggerDELETE(requestBody, headers);
                    } else {
                        yield client.triggerDELETENoBody(headers);
                    }
                }
                case OPTIONS -> client.triggerOPTIONS(headers);
                case PATCH -> {
                    if (requestBody != null && !requestBody.isEmpty()) {
                        yield client.triggerPATCH(requestBody, headers);
                    } else {
                        yield client.triggerPATCHNoBody(headers);
                    }
                }
                case TRACE -> client.triggerTRACE(headers);
                case HEAD -> client.triggerHEAD(headers);
            };
            if (response != null) {
                Instant now = Instant.now();
                CacheData data = new CacheData(headers.getFirst(CONTENT_TARGET), hash, response, now, now.plus(expiresIn, ChronoUnit.SECONDS));
                repository.save(data);
            } else {
                log.info("Response is null");
            }
            return new MockData(response, DataSource.REMOTE, hash);
        }
    }

    @Override
    @Transactional
    public void deleteCacheBefore(Instant time) {
        repository.deleteCacheBefore(time);
    }
}
