package com.app.main.configuration;

import java.net.InetSocketAddress;

import com.app.main.criptograpy.ValidatorKeyStore;
import com.app.main.network.ImpClientMina;
import com.app.main.network.ImpServerMina;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class AppConfigurations {
    private static final int DEFAULT_PORT = 1521;

    @Bean
    public ValidatorKeyStore validatekeystore() {
        return new ValidatorKeyStore();

    }

    @Bean
    public InetSocketAddress inetSocketAddress(){

        return new InetSocketAddress(DEFAULT_PORT);
    }

    @Bean
    public ImpServerMina impServerMina(){

        return new ImpServerMina();
    }

    
    @Bean
    public ImpClientMina impClientMina(){

        return new ImpClientMina();
    }

    

}
