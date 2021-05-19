package com.boilerplate.demo.helper.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.boilerplate.demo.helper.date.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class DdMmYyyyDateDeserializer extends JsonDeserializer<Long> {

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        if(jsonParser == null || StringUtils.isBlank(jsonParser.getValueAsString())){
            return null;
        }
        return DateUtils.stringToEpochMilli(jsonParser.getValueAsString(), DateUtils.DDMMYYYY);
    }
}

