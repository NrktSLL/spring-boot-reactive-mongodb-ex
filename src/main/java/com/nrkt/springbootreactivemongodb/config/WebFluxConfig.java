package com.nrkt.springbootreactivemongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebFluxConfig implements WebFluxConfigurer {
    @Override
    public void configureContentTypeResolver(RequestedContentTypeResolverBuilder builder) {
        builder.parameterResolver().parameterName("mediaType")
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("hal", MediaTypes.HAL_JSON);
    }
}
