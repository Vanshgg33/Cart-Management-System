package com.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BasicWebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .mediaType("css", MediaType.valueOf("text/css"))
                .mediaType("png", MediaType.valueOf("image/png"))
                .mediaType("jpg", MediaType.valueOf("image/jpeg"))
                .mediaType("jpeg", MediaType.valueOf("image/jpeg"))
                .mediaType("gif", MediaType.valueOf("image/gif"))
                .mediaType("svg", MediaType.valueOf("image/svg+xml"))
                .mediaType("webp", MediaType.valueOf("image/webp"))
                .mediaType("ico", MediaType.valueOf("image/x-icon"));
    }
}