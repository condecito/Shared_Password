package com.app.main.configuration;

import com.app.main.criptograpy.ValidatorKeyStore;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class AppConfigurations {
    @Bean
    public ValidatorKeyStore validatekeystore() {
        return new ValidatorKeyStore();

    }

}
