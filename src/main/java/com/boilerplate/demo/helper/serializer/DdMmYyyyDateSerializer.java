package com.boilerplate.demo.helper.serializer;

import com.boilerplate.demo.helper.date.DateUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DdMmYyyyDateSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long longDate, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        if(longDate == null){
            jsonGenerator.writeNull();
            return;
        }
        jsonGenerator.writeString(DateUtils.epochMilliToString(longDate, DateUtils.DDMMYYYY));
    }
}

