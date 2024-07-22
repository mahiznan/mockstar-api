package com.span.mockstar.client;

import com.span.mockstar.config.exception.BadRequestException;
import com.span.mockstar.config.exception.InternalServerError;
import com.span.mockstar.config.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        System.out.println("Inside decode method");
        return switch (response.status()) {
            case 400 -> new BadRequestException("Bad Request");
            case 404 -> new NotFoundException("Given URL Not found!");
            case 500 -> new InternalServerError("Internal Server Error!");
            default -> new Exception("Generic error");
        };
    }
}
