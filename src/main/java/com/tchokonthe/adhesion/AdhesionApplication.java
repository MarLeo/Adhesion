package com.tchokonthe.adhesion;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class AdhesionApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = run(AdhesionApplication.class, args);
    }
}
