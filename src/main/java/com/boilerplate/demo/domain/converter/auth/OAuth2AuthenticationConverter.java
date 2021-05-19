package com.boilerplate.demo.domain.converter.auth;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.SerializationUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OAuth2AuthenticationConverter implements AttributeConverter<OAuth2Authentication, byte[]> {

    @Override
    public OAuth2Authentication convertToEntityAttribute(byte[] dbData) {
        return (OAuth2Authentication) SerializationUtils.deserialize(dbData);
    }

    @Override
    public byte[] convertToDatabaseColumn(OAuth2Authentication authentication) {
        return SerializationUtils.serialize(authentication);
    }
}