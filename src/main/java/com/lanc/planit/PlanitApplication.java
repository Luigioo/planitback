package com.lanc.planit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("com.lanc.planit.dao")
//@EntityScan("com.lanc.planit.model")
public class PlanitApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanitApplication.class, args);
    }

}
