package com.boilerplate.demo.helper.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class CustomDateDeserializer extends JsonDeserializer<Long> {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        if(jsonParser == null || StringUtils.isBlank(jsonParser.getValueAsString())){
            return null;
        }
        LocalDateTime dateTime = null;
        if(jsonParser.getValueAsString().matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")){
            dateTime = LocalDate.parse(jsonParser.getValueAsString(), this.dateFormatter).atStartOfDay();
        } else {
            dateTime = LocalDateTime.parse(jsonParser.getValueAsString(), this.dateTimeFormatter);
        }
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }
}

