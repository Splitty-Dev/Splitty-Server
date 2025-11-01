package com.chaegangjo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients("com.chaegangjo.openfeign")
@EnableJpaAuditing
@EntityScan(basePackages = "com.chaegangjo")
@EnableJpaRepositories(basePackages = "com.chaegangjo")
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.chaegangjo")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
