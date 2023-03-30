package HealthAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HealthApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthApiApplication.class, args);
    }

}