package com.span.mockstar.config.filter;

import com.span.mockstar.config.Config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.span.mockstar.config.security.ApplicationConfiguration.*;


@Component
public class HashingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(HashingFilter.class);
    private static final String PATTERN = "yfj/mock";
    private final Config config;

    public HashingFilter(Config config) {
        this.config = config;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith(PATTERN)) {
            List<String> params = new ArrayList<>();
            String requestUri = request.getRequestURI();
            String targetKey = requestUri.split("/")[3];
            Config.ClientDetails clientDetails = config.getList().get(targetKey);
            String targetPath = requestUri.replaceFirst(PATTERN + targetKey, clientDetails.context());
            params.add(requestUri);
            params.add(request.getMethod());
            params.add(request.getContentType());
            params.add(request.getContentType());
            if (clientDetails.header() != null) {
                clientDetails.header().forEach(header -> {
                    String headerValue = request.getHeader(header);
                    params.add(headerValue);
                });
            }
            byte[] requestBody = StreamUtils.copyToByteArray(request.getInputStream());
            String hash = computeHash(requestBody, params);
            request.setAttribute(REQUEST_HASH, hash);
            request.setAttribute(CONTENT_TARGET, clientDetails.context());
            request.setAttribute(CONTENT_TARGET_PATH, targetPath);
            request.setAttribute(CONTENT_EXPIRES_IN, clientDetails.expiresInSec());
            logger.info("Exiting hashing filter with {}", hash);
        }
        filterChain.doFilter(request, response);
    }

    private String computeHash(byte[] requestBody, List<String> params) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            if (requestBody != null) {
                digest.update(requestBody);
            }
            for (String param : params) {
                if (param != null) {
                    digest.update(param.getBytes());
                }
            }
            return Base64.getEncoder().encodeToString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
