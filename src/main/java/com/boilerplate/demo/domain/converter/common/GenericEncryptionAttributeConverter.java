package com.boilerplate.demo.domain.converter.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

public abstract class GenericEncryptionAttributeConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericEncryptionAttributeConverter.class);
    private static final SecretKeyFactory keyFactory;
    private static final IvParameterSpec params;
    protected static final String CRYPTOGRAPHY_PREFIX = "{3DES}";

    @Value("${cryptography.key}")
    private String cryptographyKey;

    static {
        try {
            keyFactory = SecretKeyFactory.getInstance("DESede"); // Get the secret key factor for generating DESede keys
            params = new IvParameterSpec(new byte[] {7, 14, 21, 28, 9, 18, 27, 36} ); // Create an initialization vector (necessary for CBC mode)
        } catch (final NoSuchAlgorithmException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new RuntimeException();
        }
    }

    public GenericEncryptionAttributeConverter() {
        super();
    }

    public String getCryptographyKey() {
        return this.cryptographyKey;
    }

    public void setCryptographyKey(final String cryptographyKey) {
        this.cryptographyKey = cryptographyKey;
    }

    //http://www.quepublishing.com/articles/article.aspx?p=26343&seqNum=4
    protected byte[] encrypt(final byte[] input){
        if (Objects.isNull(input)) {
            return input;
        }
        try {
            final byte[] convertedData = convertData(Cipher.ENCRYPT_MODE, input);
            return convertedData;
        } catch (final Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new RuntimeException();
        }
    }

    //http://www.quepublishing.com/articles/article.aspx?p=26343&seqNum=4
    protected byte[] decrypt(final byte[] input) {
        if (Objects.isNull(input)) {
            return input;
        }
        try {
            final byte[] convertedData = convertData(Cipher.DECRYPT_MODE, input);
            return convertedData;
        } catch (final IllegalBlockSizeException ex) {
            LOGGER.error("Error while decrypting database value. It seems the data was not base64 encoded. You can ignore this exception");
            throw new RuntimeException();
        } catch (final Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw new RuntimeException();
        }
    }

    private byte[] convertData(Integer mode, final byte[] input) throws InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        final DESedeKeySpec spec = new DESedeKeySpec(this.cryptographyKey.getBytes()); // Create a DESede key spec from the key
        final SecretKey key = keyFactory.generateSecret(spec); // Generate a DESede SecretKey object
        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding"); // Create a DESede Cipher
        cipher.init(mode, key, params);  //Initialize the cipher and put it in decrypt mode
        final byte[] convertedData = cipher.doFinal(input); // Decrypt the data
        return convertedData;
    }
}
