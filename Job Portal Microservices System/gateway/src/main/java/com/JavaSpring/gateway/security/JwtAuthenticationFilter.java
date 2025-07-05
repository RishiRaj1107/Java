package com.JavaSpring.gateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzQ5MTAzMzk5LCJleHAiOjE3NDkxODk3OTl9.GWcifZAwNF71cIEMQoIoGvP6djKh2qaZu2ON87s5b2I";

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            logger.info("Processing request to: {}", exchange.getRequest().getPath());
            
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                logger.error("No authorization header found");
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            logger.info("Authorization header: {}", authHeader);
            
            if (!authHeader.startsWith("Bearer ")) {
                logger.error("No Bearer token found in header");
                return onError(exchange, "No Bearer token", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);
            logger.info("Extracted token: {}", token);
            
            if (!token.equals(VALID_TOKEN)) {
                logger.error("Invalid token");
                return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
            }

            logger.info("Token validation successful");
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        logger.error("Authentication error: {}", err);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add("Content-Type", "application/json");
        byte[] bytes = ("{\"error\": \"" + err + "\"}").getBytes(StandardCharsets.UTF_8);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }

    public static class Config {
        // Configuration properties if needed
    }
} 