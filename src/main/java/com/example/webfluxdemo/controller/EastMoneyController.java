package com.example.webfluxdemo.controller;

import com.example.webfluxdemo.service.ServerSentEventsConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.Loggers;

/**
 * @author ZuoLS
 * @date 2021/1/15
 */
@Slf4j
@RestController
@RequestMapping(value = "/sse")
public class EastMoneyController {

    @Autowired
    private ServerSentEventsConsumerService sseConsumer;

    @RequestMapping(value = "/last",method = RequestMethod.GET,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> lastQuote() {
        Loggers.useSl4jLoggers();
        return sseConsumer.consume()
                .map(ServerSentEvent::data)
                .log(Loggers.getLogger(EastMoneyController.class));
    }
}
