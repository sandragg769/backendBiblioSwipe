package com.biblioswipe.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Esto hace que si pides /uploads/foto.jpg, Spring busque en la carpeta real del PC
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}