package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EntityScan(basePackages = {"org.example.model"})
public class SpringBootSecurity3_1_2_Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurity3_1_2_Application.class, args);
    }
}
