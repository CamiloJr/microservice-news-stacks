package com.udemy.gateway.configurations;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfig {

    // nao ha necessidade destas configuracoes se foi configuradas no application.yml
//
//    @Bean
//    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
//        Function<PredicateSpec, Buildable<Route>> function =
//                p -> p.path("/get")
//                        .filters(f -> f
//                                .addRequestHeader("Hello", "World")
//                                .addRequestParameter("Hello", "World"))
//                        .uri("http://httpbin.org:80");
//        return builder.routes()
//                .route(function)
//                .route(p -> p.path("/cambio-service/**").uri("lb://cambio-ms"))
//                .route(p -> p.path("/book-service/**").uri("lb://book-ms"))
//                .build();
//    }
}
