package com.sbyte.shield.configurations.policy;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShieldPolicyConfigurations {

    @Bean
    public ShieldPolicy policyConfig() {
        return new ShieldPolicy();
    }
}
