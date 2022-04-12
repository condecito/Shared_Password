package com.app.main.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorKeyStore {

    private static Logger logger = LoggerFactory.getLogger(ValidatorKeyStore.class);
    private static final String KEYSTORE_ALGORITHM = "JCEKS";
    private static final String KEYSTORE_FILE = System.getProperty("user.name").trim();
    private static String KEYSTORE_EXTENSION = ".jks";
    private static KeyStore keyStore;
    private static final String OS_SEPARATOR = File.separator;
    private static final String KEYSTORE_PATH = System.getProperty("user.home") + OS_SEPARATOR + KEYSTORE_FILE
            + KEYSTORE_EXTENSION;
    private static final String KEYSTORE_ALIAS = KEYSTORE_FILE;
    private static Cipher dCipher;
    private static Cipher eCipher;

    public boolean LoadKeyStoreFromFile(char[] keyStorePassword) {
        boolean availableKeyStore = false;
        logger.info("LoadKeyStoreFromFile  : {}", "Load KeyStore");
        logger.info("KeyStore PATH :{} ", KEYSTORE_PATH);
        logger.info("KeyStore FILE :{}", KEYSTORE_FILE);

        try {
            keyStore = KeyStore.getInstance(KEYSTORE_ALGORITHM);
            File keyStoreFile = new File(KEYSTORE_PATH);
            if (keyStoreFile.exists()) {
                InputStream stream = new FileInputStream(keyStoreFile);
                keyStore.load(stream, keyStorePassword);
                stream.close();
                logger.info("Success load KeyStore", KEYSTORE_FILE);
                availableKeyStore = true;
            }

        } catch (IOException ioException) {
            logger.error("IOException {}", ioException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            logger.error("NoSuchAlgorithmException {}", noSuchAlgorithmException);
        } catch (CertificateException certificateException) {
            logger.error("CertificateException {}", certificateException);
        } catch (KeyStoreException keyStoreException) {
            logger.error("KeyStoreException {}", keyStoreException);
        }
        return availableKeyStore;

    }

    public boolean createKeyStore(char[] keyStorePassword) {
        boolean availableKeyStore = false;
        logger.info("createKeyStore  :{}", "create KeyStore");
        logger.info("KeyStore PATH :{} ", KEYSTORE_PATH);
        logger.info("KeyStore FILE :{}", KEYSTORE_FILE);

        try {

            // File keyStoreFile = new File(KEYSTORE_PATH);
            FileOutputStream stream = new FileOutputStream(KEYSTORE_PATH);
            // LoadStoreParameter parameter
            // if (!keyStoreFile.exists()) {
            // keyStore = KeyStore.getInstance(KEYSTORE_ALGORITHM);
            keyStore = KeyStore.getInstance(KEYSTORE_ALGORITHM);
            keyStore.load(null, keyStorePassword);
            keyStore.store(stream, keyStorePassword);
            stream.close();
            logger.info("Success create FILE :{}", KEYSTORE_PATH);
            // } else {
            // logger.info("KeyStore Already exists:{}", KEYSTORE_PATH);
            // }

            availableKeyStore = true;
        } catch (IOException ioException) {
            logger.error("IOException  {}", ioException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            logger.error("NoSuchAlgorithmException { }", noSuchAlgorithmException);
        } catch (CertificateException certificateException) {
            logger.error("CertificateException { }", certificateException);
        } catch (KeyStoreException keyStoreException) {
            logger.error("KeyStoreException {}", keyStoreException);
        }

        return availableKeyStore;
    }

    public boolean LoadPassEncript(byte[] passwordToEncript, char[] keyStorePassword) throws KeyStoreException {
        boolean successEnciptation = false;
        logger.info("PassToEncript  Text : {} ", "Encripting...");

        Key key = null;
        try {
            if (LoadKeyStoreFromFile(keyStorePassword)) {
                if (keyStore.isCertificateEntry(KEYSTORE_ALIAS)) {
                    Certificate certificate = keyStore.getCertificate(KEYSTORE_ALIAS);
                    key = certificate.getPublicKey();
                } else {
                    key = keyStore.getKey(KEYSTORE_ALIAS, keyStorePassword);
                }
                eCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                eCipher.init(Cipher.ENCRYPT_MODE, key);
                Base64.encodeBase64String(eCipher.doFinal(passwordToEncript));
                successEnciptation = true;
            }
        } catch (UnrecoverableKeyException unrecoverableKeyException) {
            logger.error("UnrecoverableKeyException {}", unrecoverableKeyException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // TODO Auto-generated catch block
            logger.error("NoSuchAlgorithmException {}", noSuchAlgorithmException);
        } catch (NoSuchPaddingException noSuchPaddingException) {
            // TODO Auto-generated catch block
            logger.error("NoSuchPaddingException {}", noSuchPaddingException);
        } catch (InvalidKeyException invalidKeyException) {
            // TODO Auto-generated catch block
            logger.error("InvalidKeyException {}", invalidKeyException);
        } catch (IllegalBlockSizeException illegalBlockSizeException) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error("IllegalBlockSizeException {}", illegalBlockSizeException);
        } catch (BadPaddingException badPaddingException) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error("IllegalBlockSizeException {}", badPaddingException);
        }

        return successEnciptation;

    }

    public boolean EncriptPassOnKeyStore(char[] PasswordToEncript, char[] keyStorePassword) {
        boolean savedSuccess = false;
        if (LoadKeyStoreFromFile(keyStorePassword)) {
            KeyStore.SecretKeyEntry secreEntry;

          //  keyStore.setEntry(KEYSTORE_ALIAS, secreEntry, protParam);
        }

        return savedSuccess;

    }

}
