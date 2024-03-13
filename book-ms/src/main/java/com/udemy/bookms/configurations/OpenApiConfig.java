package com.udemy.bookms.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition(info = @Info(title = "Book Service API (@Title)",
                                version = "v1.0 (@Version)",
                                description = "Documentation of Book Service API (@Description)")
)
public class OpenApiConfig {

    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Book Service API")
                        .version("v1.0")
                        .license(new License()
                                .name("MIT Licence")
                                .url("pixr.studio"))
                );
    }

}
