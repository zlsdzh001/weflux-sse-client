package com.example.webfluxdemo.service.impl;

import com.example.webfluxdemo.service.ServerSentEventsConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * @author ZuoLS
 * @date 2021/1/14
 */
@Service
@RequiredArgsConstructor
public class ServerSentEventsConsumerServiceImpl implements ServerSentEventsConsumerService {

    private final WebClient webCLient;

    private ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<>() {
    };

    @Override
    public Flux<ServerSentEvent<String>> consume(){
        return webCLient.get()
                .uri("/")
                .retrieve()
                .bodyToFlux(type);
    }

}