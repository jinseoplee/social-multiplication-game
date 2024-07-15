package com.ljs.smg.filter;

import com.ljs.smg.client.TokenValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final WebClient webClient;

    public AuthenticationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClient = webClientBuilder.build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            String accessToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (accessToken == null || !accessToken.startsWith("Bearer ")) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            accessToken = accessToken.substring(7);

            return webClient.get()
                    .uri("http://localhost:8000/api/v1/auth/validate-token?token=" + accessToken)
                    .retrieve()
                    .bodyToMono(TokenValidationResponse.class)
                    .flatMap(tokenValidationResponse -> {
                        if (tokenValidationResponse.isValid()) {
                            return chain.filter(exchange);
                        } else {
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            return response.setComplete();
                        }
                    });
        };
    }

    public static class Config {
    }
}
