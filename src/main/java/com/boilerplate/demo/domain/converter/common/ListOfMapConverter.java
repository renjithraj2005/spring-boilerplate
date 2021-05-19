package com.boilerplate.demo.domain.converter.common;

import com.boilerplate.demo.helper.utils.StringHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Converter
public class ListOfMapConverter implements AttributeConverter<List<Map>, String> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Map> convertToEntityAttribute(String values) {
        if(StringUtils.isBlank(values)){
            return new ArrayList<>(0);
        }
        try {
            return objectMapper.readValue(StringHelper.unescapeJson(values), new TypeReference<List<Map>>(){});
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    @Override
    public String convertToDatabaseColumn(List<Map> values) {
        if(!(values instanceof List) || values.isEmpty()){
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
