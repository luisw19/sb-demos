package com.sample.api;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * GreetingHandler
 */
@Component
public class GreetingHandler {

    public Mono<ServerResponse> hola (ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
			.body(BodyInserters.fromObject("Hola Mundo"));
    }
    
    public Mono<ServerResponse> hello (ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
			.body(BodyInserters.fromObject("Hello World"));
    }
}