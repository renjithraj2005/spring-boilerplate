package com.boilerplate.demo.helper.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.boilerplate.demo.domain.model.auth.Role;

import java.io.IOException;
import java.util.List;

public class RoleSerializer extends JsonSerializer<List<Role>> {

    @Override
    public void serialize(List<Role> roles, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        if(roles == null){
            jsonGenerator.writeArray(new int[0], 0, 0);
            return;
        }
        jsonGenerator.writeStartArray();
        roles.forEach(role -> {
            try{
                jsonGenerator.writeString(role.getAuthority());
            } catch (Exception exp){
                exp.printStackTrace();
            }
        });
        jsonGenerator.writeEndArray();
    }
}
