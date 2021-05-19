package com.boilerplate.demo.domain.converter.auth;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.SerializationUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OAuth2RefreshTokenConverter implements AttributeConverter<OAuth2RefreshToken, byte[]> {

    @Override
    public OAuth2RefreshToken convertToEntityAttribute(byte[] refreshToken) {
        return (OAuth2RefreshToken) SerializationUtils.deserialize(refreshToken);
    }

    @Override
    public byte[] convertToDatabaseColumn(OAuth2RefreshToken refreshToken) {
        return SerializationUtils.serialize(refreshToken);
    }
}