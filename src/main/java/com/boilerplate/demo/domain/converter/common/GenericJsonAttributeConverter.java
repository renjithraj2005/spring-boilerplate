package com.boilerplate.demo.domain.converter.common;

import java.io.IOException;
import javax.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;


public class GenericJsonAttributeConverter<X> implements AttributeConverter<X, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericJsonAttributeConverter.class);

    private ObjectWriter writer;
    private ObjectReader reader;

    public GenericJsonAttributeConverter() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.ALWAYS);
        mapper.setVisibility(mapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
        reader = mapper.reader().forType(JsonType.class);
        writer = mapper.writer().forType(JsonType.class);
    }

    @Override
    public String convertToDatabaseColumn(X attribute) {
        try {
            if (attribute != null) {
                JsonType<X> wrapper = new JsonType<X>(attribute, writer);
                String value = writer.writeValueAsString(wrapper);
                return value;
            } else {
                return null;
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to serialize as object into JSON: {}", attribute,
                    e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public X convertToEntityAttribute(String dbData) {
        try {
            if (dbData != null) {
                JsonType<X> wrapper = reader.readValue(dbData);
                X obj = wrapper.readValue(reader);
                return obj;
            } else {
                return null;
            }
        } catch (IOException e) {
            LOGGER.error("Failed to deserialize as object from JSON: {}", dbData,
                    e);
            throw new RuntimeException(e);
        }
    }

    public static class JsonType<Y> {

        private String entityType;
        private String entityValue;

        public JsonType() {
        }

        public JsonType(Y obj, ObjectWriter writer) {
            Class<Y> classType = ((Class<Y>) obj.getClass()); this.entityType = obj.getClass().getName();
            try {
                this.entityValue = writer.forType(classType)
                        .writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                LOGGER.error("Failed serializing object to JSON: {}", obj, e);
            }
        }

        public Y readValue(ObjectReader reader) {
            try {
                Class<?> clazz = Class.forName(this.entityType);
                Y obj = reader.forType(clazz).readValue(this.entityValue);
                return obj;
            } catch (ClassNotFoundException | IOException e) {
                LOGGER.error("Failed deserializing object from JSON: {}", this.entityValue, e);
                return null;
            }
        }

        public String getEntityType() {
            return entityType;
        }

        public void setEntityType(String type) {
            this.entityType = type;
        }

        public String getEntityValue() {
            return entityValue;
        }

        public void setValue(String value) {
            this.entityValue = value;
        }
    }
}