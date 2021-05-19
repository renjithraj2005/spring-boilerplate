package com.boilerplate.demo.domain.converter.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

@Converter
@Component
public class OAuth2ClientAdditionalDetailsConverter implements AttributeConverter<Map<String, Object>, String> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public Map<String, Object> convertToEntityAttribute(String additionalInfo) {
        if(StringUtils.isBlank(additionalInfo)){
            return new LinkedHashMap<>(0);
        }
        try {
            return mapper.readValue(additionalInfo, Map.class);
        } catch (IOException exp) {
            exp.printStackTrace();
            return new LinkedHashMap<>(0);
        }
    }

    @Override
    public String convertToDatabaseColumn(Map<String, Object> additionalInfo) {
        if(!(additionalInfo instanceof Map) || additionalInfo.size() == 0){
            return null;
        }
        try {
            return mapper.writeValueAsString(additionalInfo);
        } catch (IOException exp) {
            exp.printStackTrace();
            return null;
        }
    }
}