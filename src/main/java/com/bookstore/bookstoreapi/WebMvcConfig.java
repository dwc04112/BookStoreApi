package com.bookstore.bookstoreapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("C:/Spring/BookStoreApi/profile_imgs/")
    private String profileUploadFolder;

    public void addResourceHandlers(ResourceHandlerRegistry registry){
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry
                .addResourceHandler("/profile_imgs/**")
                .addResourceLocations("file://"+profileUploadFolder)
                .setCachePeriod(60*10*6)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
