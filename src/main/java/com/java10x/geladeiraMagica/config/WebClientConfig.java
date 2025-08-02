package com.java10x.geladeiraMagica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${API_URL}")
    private String chatGptApiUrl;
    
    @Bean
    public WebClient webClient (WebClient.Builder builder){
        return builder.baseUrl(chatGptApiUrl).build();
    }
}
