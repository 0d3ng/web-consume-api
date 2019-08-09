package com.sinaungoding.web.consumeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
public class ConsumeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumeApiApplication.class, args);
    }

    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
