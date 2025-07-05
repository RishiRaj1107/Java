package com.JavaSpring.gateway.config;

import com.JavaSpring.gateway.security.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Configuration
public class FilterConfig {

    @Component
    public static class TokenValidationFilter extends AbstractGatewayFilterFactory<TokenValidationFilter.Config> {
        private final JwtUtil jwtUtil;

        public TokenValidationFilter(JwtUtil jwtUtil) {
            super(Config.class);
            this.jwtUtil = jwtUtil;
        }

        @Override
        public GatewayFilter apply(Config config) {
            return (exchange, chain) -> {
                String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
                
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    return onError(exchange, "No valid token found", HttpStatus.UNAUTHORIZED);
                }

                String token = authHeader.substring(7);
                if (!jwtUtil.validateToken(token)) {
                    return onError(exchange, "Invalid token", HttpStatus.UNAUTHORIZED);
                }

                return chain.filter(exchange);
            };
        }

        private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
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

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, TokenValidationFilter tokenFilter) {
        return builder.routes()
                .route("companyMS", r -> r
                        .path("/companies/**")
                        .filters(f -> f.filter(tokenFilter.apply(new TokenValidationFilter.Config())))
                        .uri("http://localhost:8081"))
                .route("job_service", r -> r
                        .path("/jobs/**")
                        .filters(f -> f.filter(tokenFilter.apply(new TokenValidationFilter.Config())))
                        .uri("http://localhost:8082"))
                .route("review_service", r -> r
                        .path("/reviews/**")
                        .filters(f -> f.filter(tokenFilter.apply(new TokenValidationFilter.Config())))
                        .uri("http://localhost:8083"))
                .route("eureka-server", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }
} 