package com.tchokonthe.adhesion.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

        try(InputStream in = Files.newInputStream(Paths.get("src/main/conf/jwt.yml"))) {
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
}
