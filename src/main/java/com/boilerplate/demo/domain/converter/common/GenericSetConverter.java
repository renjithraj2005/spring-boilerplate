package com.boilerplate.demo.domain.converter.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.*;
import java.util.stream.Collectors;

@Converter
public class GenericSetConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public Set<String> convertToEntityAttribute(String values) {
        if(StringUtils.isBlank(values)){
            return new LinkedHashSet<>(0);
        }
        return new LinkedHashSet<>(Arrays.asList(values.split(",")));
    }

    @Override
    public String convertToDatabaseColumn(Set<String> values) {
        if(!(values instanceof Set) || values.isEmpty()){
            return null;
        }
        return values.stream().collect(Collectors.joining(","));
    }
}