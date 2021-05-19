package com.boilerplate.demo.domain.converter.auth;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class AuthoritiesConverter implements AttributeConverter<List<GrantedAuthority>, String> {

    @Override
    public List<GrantedAuthority> convertToEntityAttribute(String authorities) {
        if(StringUtils.isBlank(authorities)){
            return new ArrayList<GrantedAuthority>(0);
        }
        return AuthorityUtils.createAuthorityList(authorities.split(","));
    }

    @Override
    public String convertToDatabaseColumn(List<GrantedAuthority> authorities) {
        if(!(authorities instanceof List) || authorities.isEmpty()){
            return null;
        }
        return authorities.stream().map(authority -> authority.getAuthority()).collect(Collectors.joining(","));
    }
}