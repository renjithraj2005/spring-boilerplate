package com.boilerplate.demo.domain.converter.auth;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.SerializationUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OAuth2AccessTokenConverter implements AttributeConverter<OAuth2AccessToken, byte[]> {

    @Override
    public OAuth2AccessToken convertToEntityAttribute(byte[] token) {
        return (OAuth2AccessToken) SerializationUtils.deserialize(token);
    }

    @Override
    public byte[] convertToDatabaseColumn(OAuth2AccessToken token) {
        return SerializationUtils.serialize(token);
    }
}