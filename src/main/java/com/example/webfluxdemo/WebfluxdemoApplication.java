package com.example.webfluxdemo;

import com.example.webfluxdemo.service.ServerSentEventsConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import java.time.LocalTime;

@Slf4j
@SpringBootApplication
public class WebfluxdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxdemoApplication.class, args);
    }

//    @Bean
    public CommandLineRunner start(ServerSentEventsConsumerService service) {
        return args -> {
            Flux<ServerSentEvent<String>> eventStream = service.consume();

            eventStream.subscribe(ctx ->
                    log.info("Current time: {}, content[{}] ", LocalTime.now(), ctx.data()));
        };
    }

}
