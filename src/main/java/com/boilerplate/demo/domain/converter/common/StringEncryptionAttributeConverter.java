package com.boilerplate.demo.domain.converter.common;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Base64;

@Component
@Converter
public class StringEncryptionAttributeConverter extends GenericEncryptionAttributeConverter implements AttributeConverter<String, String> {

    private final Logger logger = LoggerFactory.getLogger(StringEncryptionAttributeConverter.class);

    @Override
    public String convertToDatabaseColumn(final String attribute) {
        if (StringUtils.isBlank(attribute)) {
            return null;
        }
        final byte[] input = attribute.getBytes();
        final byte[] encrypted = super.encrypt(input);
        return Base64.getEncoder().encodeToString(encrypted);
    }

    @Override
    public String convertToEntityAttribute(final String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return null;
        }
        try{
            final byte[] input = Base64.getDecoder().decode(dbData);
            final byte[] decrypted = super.decrypt(input);
            return new String(decrypted);
        }catch (Exception exp){
            logger.error("Value: " + dbData + ", was expected to be encoded.");
            exp.printStackTrace();
            return dbData;
        }
    }
}
