package com.span.mockstar.config.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
        Status status = new Status(ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI());
        return handleExceptionInternal(ex, status, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    protected ResponseEntity<Object> handleBadRequest(BadRequestException ex, WebRequest request) {
        Status status = new Status(ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI());
        return handleExceptionInternal(ex, status, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        Status status = new Status(ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(status, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {InternalServerError.class})
    protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        Status status = new Status(ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI());
        return handleExceptionInternal(ex, status, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}

record Status(String message, String uri) {
}
