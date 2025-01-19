package com.mind_threads.core.extension_service.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class GptConfig {
    @Value("${openai.api-key}")
    private String secretKey;

    @Value("${openai.model}")
    private String model;

    @Bean
    public static RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }
}
