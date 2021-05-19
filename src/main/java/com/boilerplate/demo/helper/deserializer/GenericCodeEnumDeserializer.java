package com.boilerplate.demo.helper.deserializer;

import com.boilerplate.demo.constants.CodeEnum;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;

public class GenericCodeEnumDeserializer<T extends Enum & CodeEnum> extends JsonDeserializer<T> {

    private final T[] values;
    public GenericCodeEnumDeserializer(T[] values)  {
        this.values = values;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        if(jsonParser == null){
            return null;
        }
        String code = jsonParser.getValueAsString();
        return Arrays.stream(values)
                .filter(x -> code.equals(x.getCode()))
                .findFirst().orElse(null);
    }
}
