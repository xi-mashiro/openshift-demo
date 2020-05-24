package com.example.democlient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class DemoclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoclientApplication.class, args);
    }

}
