package com.sbyte.shield;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication(scanBasePackages = {"com.sbyte.shield.*"})
@MapperScan("com.sbyte.shield.datasource.mybatis")
@EnableJpaRepositories(basePackages = "com.sbyte.shield.datasource.jpa")
@EnableConfigurationProperties
@EnableScheduling()

public class ShieldApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShieldApplication.class, args);
    }
}
