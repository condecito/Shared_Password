package com.app.main.configuration;

import java.net.InetSocketAddress;

import com.app.main.criptograpy.ValidatorKeyStore;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class AppConfigurations {
    private static final int DEFAULT_PORT = 1520;

    @Bean
    public ValidatorKeyStore validatekeystore() {
        return new ValidatorKeyStore();

    }

    @Bean
    public InetSocketAddress inetSocketAddress(){

        return new InetSocketAddress(DEFAULT_PORT);
    }

}
