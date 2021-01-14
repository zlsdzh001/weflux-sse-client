package com.example.webfluxdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author ZuoLS
 * @date 2021/1/14
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.create("http://127.0.0.1:8080/sse/countDown/");
    }
}