package com.toy.pbzuul;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;
import java.util.stream.Collectors;

@Profile("!prod")
@Component
@Primary
@EnableAutoConfiguration
public class SwaggerController implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;

    public SwaggerController(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        return routeLocator.getRoutes().stream().distinct().map(route -> {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(route.getLocation());
            swaggerResource.setLocation(route.getFullPath().replace("**", "/v2/api-docs"));
            swaggerResource.setSwaggerVersion(DocumentationType.SWAGGER_2.getVersion());

            return swaggerResource;

        }).collect(Collectors.toList());
    }
}
