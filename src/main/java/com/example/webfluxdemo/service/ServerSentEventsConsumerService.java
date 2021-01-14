package com.example.webfluxdemo.service;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

/**
 * @author ZuoLS
 * @date 2021/1/14
 */
public interface ServerSentEventsConsumerService {
    Flux<ServerSentEvent<String>> consume();
}