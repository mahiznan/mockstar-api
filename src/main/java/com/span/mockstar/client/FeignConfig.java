package com.span.mockstar.client;

import feign.Logger;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;


public class FeignConfig {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeignConfig.class);
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    /*
    @Bean
    Decoder feignDecoder() {
        var jsonMessageConverters = new MappingJackson2HttpMessageConverter(new ObjectMapper());
        return new ResponseEntityDecoder(new SpringDecoder(() -> {
            System.out.println("Inside feignDecoder");
            return new HttpMessageConverters(jsonMessageConverters);
        }));
    }*/

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            logger.debug("Inside requestInterceptor");
            requestTemplate.header("user", "ajeje");
            requestTemplate.header("password", "brazof");
            requestTemplate.header("Accept", "application/json");
        };
    }

    // @Bean - uncomment to use this interceptor and remove @Bean from the requestInterceptor()
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("ajeje", "brazof");
    }
}
