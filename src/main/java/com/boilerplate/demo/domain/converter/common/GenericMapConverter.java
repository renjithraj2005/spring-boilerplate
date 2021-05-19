package com.boilerplate.demo.domain.converter.common;

import com.boilerplate.demo.helper.utils.StringHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.LinkedHashMap;
import java.util.Map;

@Converter
public class GenericMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, Object> convertToEntityAttribute(String values) {
        if(StringUtils.isBlank(values)){
            return new LinkedHashMap<>(0);
        }
        try {
            return objectMapper.readValue(StringHelper.unescapeJson(values), LinkedHashMap.class);
        } catch (Exception exp) {
            exp.printStackTrace();
            return new LinkedHashMap<>(0);
        }
    }

    @Override
    public String convertToDatabaseColumn(Map<String, Object> values) {
        if(!(values instanceof Map) || values.isEmpty()){
            return null;
        }
        try {
            return objectMapper.writeValueAsString(values);
        } catch (Exception exp) {
            exp.printStackTrace();
            return null;
        }
    }
}