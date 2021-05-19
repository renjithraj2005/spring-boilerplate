package com.boilerplate.demo.domain.converter.common;

import com.boilerplate.demo.constants.CodeEnum;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.Arrays;

public abstract class GenericCodeEnumConverter<T extends Enum & CodeEnum> implements AttributeConverter<T, String> {

    private final T[] values;
    public GenericCodeEnumConverter(T[] values) {
        this.values = values;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getCode() : null;
    }

    @Override
    public T convertToEntityAttribute(String code) {
        if(StringUtils.isBlank(code)){
            return null;
        }
        return Arrays.stream(values)
                .filter(x -> code.equals(x.getCode()))
                .findFirst().orElse(null);
    }
}
