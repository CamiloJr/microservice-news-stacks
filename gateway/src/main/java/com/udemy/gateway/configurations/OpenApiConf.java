package com.udemy.gateway.configurations;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class OpenApiConf {

    @Autowired
    RouteDefinitionLocator locator;

    @Bean
    public List<GroupedOpenApi> apis() {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        assert definitions != null;
        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*")).forEach(routeDefinition -> {
            String name = routeDefinition.getId().replaceAll("", "");
            groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build());
        });
        return groups;
    }

//    @Bean
//    @Lazy(value = false) // Carregar assim que o API Gateway estiver inicializando
//    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters,
//                                     RouteDefinitionLocator routeDefinitionLocator) {
//        // 1. Identiricar quais ms possuem documentacao swagger
//        // 2. Agrupar
//
//        var definitions = routeDefinitionLocator.getRouteDefinitions()
//                .collectList()
//                .block();
//        definitions.stream()
//            .filter(routeDefinition -> routeDefinition
//                    .getId()
//                    .matches(".*-service")
//            )
//            .forEach(routeDefinition -> {
//                String name = routeDefinition.getId();
//                swaggerUiConfigParameters.addGroup(name);
//                GroupedOpenApi.builder()
//                        .pathsToMatch("/" + name + "/**")
//                        .group(name).build();
//            });
//        return new ArrayList<>();
//    }
//
//    @Bean
//    @Lazy(value = false)
//    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters,
//                                     RouteDefinitionLocator routeDefinitionLocator) {
//        var definitions = routeDefinitionLocator.getRouteDefinitions()
//                .collectList()
//                .block();
//
//        List<GroupedOpenApi> apis = definitions.stream()
//                .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
//                .map(routeDefinition -> {
//                    String name = routeDefinition.getId();
//                    swaggerUiConfigParameters.addGroup(name);
//                    return GroupedOpenApi.builder()
//                            .pathsToMatch("/" + name + "/**")
//                            .group(name)
//                            .build();
//                })
//                .collect(Collectors.toList());
//        return apis;
//    }
//
//    @Bean
//    @Lazy(value = false) // Carregar assim que o API Gateway estiver inicializando
//    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters,
//                                     RouteDefinitionLocator routeDefinitionLocator) {
//        List<GroupedOpenApi> groupedApis = new ArrayList<>();
//
//        var definitions = routeDefinitionLocator.getRouteDefinitions()
//                .collectList()
//                .block();
//
//        definitions.stream()
//                .filter(routeDefinition -> routeDefinition
//                        .getId()
//                        .matches(".*-service")
//                )
//                .forEach(routeDefinition -> {
//                    String name = routeDefinition.getId();
//                    swaggerUiConfigParameters.addGroup(name);
//                    GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder()
//                            .pathsToMatch("/" + name + "/**")
//                            .group(name)
//                            .build();
//                    groupedApis.add(groupedOpenApi);
//                });
//        return groupedApis;
//    }
}
