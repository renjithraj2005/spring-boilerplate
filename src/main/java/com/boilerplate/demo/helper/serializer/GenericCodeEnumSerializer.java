package com.boilerplate.demo.helper.serializer;

import com.boilerplate.demo.constants.CodeEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public abstract class GenericCodeEnumSerializer<T extends Enum & CodeEnum> extends JsonSerializer<T> {

    private final T[] values;

    public GenericCodeEnumSerializer(T[] values) {
        this.values = values;
    }

    @Override
    public void serialize(T codeEnum, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        if(codeEnum == null){
            jsonGenerator.writeNull();
            return;
        }
        jsonGenerator.writeString(codeEnum.getCode());
    }
}

