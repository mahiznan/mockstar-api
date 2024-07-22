package com.span.mockstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@ConfigurationPropertiesScan({"com.span.mockstar.config"})
@SpringBootApplication
public class MockstarApi {

	public static void main(String[] args) {
		SpringApplication.run(MockstarApi.class, args);
	}

}
