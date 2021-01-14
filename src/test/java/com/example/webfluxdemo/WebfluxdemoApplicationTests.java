package com.example.webfluxdemo;

import com.example.webfluxdemo.service.ServerSentEventsConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class WebfluxdemoApplicationTests {

    private final ServerSentEventsConsumerService service;

    //    @Test
//    @DisplayName("Consume Server Sent Event")
    public void shouldConsumeServerSentEvents(TestInfo testInfo) throws Exception {
        log.info("Running: {}", testInfo.getDisplayName());

        service.consume().subscribe(x -> System.out.println(x.data()));
        StepVerifier.create(service.consume())
                .expectNextMatches(event -> event.data().contains("活动倒计时"))
                .thenAwait(Duration.ofSeconds(5))
                .expectNextCount(1)
                .thenCancel()
                .verify();
    }

    @Test
    void test1() {
        String url = "http://75.push2.eastmoney.com/api/qt/stock/sse";
        Flux<ServerSentEvent<String>> flux = WebClient.create(url)
                .get()
                .attributes(stringObjectMap -> {
                    stringObjectMap.put("secid", "0.300059");
                    stringObjectMap.put("ut", "fa5fd1943c7b386f172d6893dbfba10b");
                    stringObjectMap.put("fltt", "2");
                    stringObjectMap.put("fields", "f43,f57,f58,f169,f170,f46,f44,f51,f168,f47,f164,f163,f116,f60,f45,f52,f50,f48,f167,f117,f71,f161,f49,f530,f135,f136,f137,f138,f139,f141,f142,f144,f145,f147,f148,f140,f143,f146,f149,f55,f62,f162,f92,f173,f104,f105,f84,f85,f183,f184,f185,f186,f187,f188,f189,f190,f191,f192,f107,f111,f86,f177,f78,f110,f260,f261,f262,f263,f264,f267,f268,f250,f251,f252,f253,f254,f255,f256,f257,f258,f266,f269,f270,f271,f273,f274,f275,f127,f199,f128,f193,f196,f194,f195,f197,f80,f280,f281,f282,f284,f285,f286,f287,f292,f181,f294,f295,f279,f288");
                })
                .headers(httpHeaders -> {
                    httpHeaders.add("Accept", "text/event-stream");
                    httpHeaders.add("Referer", "http://quote.eastmoney.com/");
                    httpHeaders.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
                })
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {
                });
        flux.subscribe(x -> System.out.println(x.data()));
        StepVerifier.create(flux)
                .expectNextMatches(event -> event.data().contains("data"))
                .thenAwait(Duration.ofSeconds(5))
                .expectNextCount(2)
                .thenCancel()
                .verify();
    }
}
