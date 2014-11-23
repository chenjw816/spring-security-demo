package com.demo.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Created by yamorn on 2014/11/23.
 */
public class Beans {
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(4294967296L);
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
//        commonsMultipartResolver.setUploadTempDir();
        return commonsMultipartResolver;
    }
}
