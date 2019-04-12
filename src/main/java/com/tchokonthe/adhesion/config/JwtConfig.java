package com.tchokonthe.adhesion.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties({JwtProperties.class})
public class JwtConfig {


    private JwtProperties jwtProperties;

    public JwtConfig() {
        Yaml yaml = new Yaml();

        try (InputStream in = Files.newInputStream(Paths.get("src/main/conf/jwt.yml"))) {
            jwtProperties = yaml.loadAs(in, JwtProperties.class);
            System.out.println("Properties: " + jwtProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean("customJwtProperties")
    public JwtProperties jwtProperties() {
        return jwtProperties;
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtProperties.getSigning_key());
//        converter.setVerifierKey(jwtProperties.getVerify_key());
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
