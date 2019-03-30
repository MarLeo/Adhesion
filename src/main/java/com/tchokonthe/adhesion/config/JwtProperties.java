package com.tchokonthe.adhesion.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "registration", ignoreUnknownFields = false)
public class JwtProperties {

    public Credentials credentials = new Credentials();

    public List<String> scopes = newArrayList();

    public List<String> grant_types = newArrayList();

    public List<String> authorities = newArrayList();

    public String access_token_validity;

    public String refresh_token_validity;

    public String signing_key;

    public String verify_key;

    public JwtProperties() {
    }

    @Data
    public static class Credentials {
        private String client_id;
        private String client_secret;
    }
}
