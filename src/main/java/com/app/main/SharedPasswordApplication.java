package com.app.main;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import com.app.main.criptograpy.KeyGenerator;
import com.app.main.criptograpy.MessageEncript;
import com.app.main.criptograpy.ValidatorKeyStore;
import com.app.main.network.ImpClientMina;
import com.app.main.network.ImpServerMina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
public class SharedPasswordApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SharedPasswordApplication.class, args);
    }

    @Autowired
    private ValidatorKeyStore validateKeyStore;

    @Autowired
    private ImpServerMina impServerMina;
    @Autowired
    private ImpClientMina impClientMina;

    @Override
    public void run(String... args) throws Exception {

        MessageEncript encriptMessage = new MessageEncript();
        KeyGenerator key = new KeyGenerator();
        PrivateKey privateKey = key.getPrivateKey();
        PublicKey publicKey = key.getPublicKey();

        //System.out.println("public key"+publicKey);
        //System.out.println("private key"+privateKey);
        //	String passToEncript = "password";
        //	String encodePass = "password";//Base64.getEncoder().encodeToString(passToEncript.getBytes());
        //String encrypted_msg = encriptMessage.encryptText(encodePass, privateKey);
        //String decrypted_msg = encriptMessage.decryptText(encrypted_msg, publicKey);
        //System.out.println("Original Message: " + encodePass +
        //		"\nEncrypted Message: " + encrypted_msg
        //		+ "\nDecrypted Message: " + decrypted_msg);
        System.out.println("Inicianlizando Server ...");
        impServerMina.start();
        Thread.sleep(1000);

        System.out.println("Inicianlizando cliente ...");
        impClientMina.start();
        System.out.println("sending..");
        impClientMina.send();

    }
}
