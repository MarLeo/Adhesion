package com.tchokonthe.adhesion;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
public class AdhesionApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = run(AdhesionApplication.class, args);
    }
}
