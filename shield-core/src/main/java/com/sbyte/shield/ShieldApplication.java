package com.sbyte.shield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication(scanBasePackages = {"com.sbyte.shield.*"})
@MapperScan("com.sbyte.shield.datasource.mybatis")
@EnableJpaRepositories(basePackages = "com.sbyte.shield.datasource.repository")
@EnableConfigurationProperties
public class ShieldApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShieldApplication.class, args);
    }
}
