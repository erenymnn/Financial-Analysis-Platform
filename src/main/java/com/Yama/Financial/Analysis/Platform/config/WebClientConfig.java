package com.Yama.Financial.Analysis.Platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() { // Parametreyi kaldırdık
        return WebClient.builder() // Builder'ı manuel başlattık
                .baseUrl("https://api.binance.com")
                .build();
    }
}