package com.vm.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import lombok.Data;
import lombok.Getter;

@Validated
@Data
@Configuration
@ConfigurationProperties(prefix = "banner")
public class BannerConfig
{

    @Valid
    @NotNull
    private String authUrl;

    @Valid
    @NotNull
    private String bannersUrl;

    @Valid
    @NotNull
    private String showBannersUrl;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder)
    {
        return builder.build();
    }

    private Token token;

    public static final class Token
    {
        @Getter
        private String token;


    }

    private Auth auth;

    @Data
    public static final class Auth
    {
        @Valid
        @NotNull
        private String username;

        @Valid
        @NotNull
        private String password;

    }
}
