package com.boilerplate.demo.helper.deserializer;

import com.boilerplate.demo.domain.model.auth.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.boilerplate.demo.repository.auth.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserDeserializer extends JsonDeserializer<User> {

    private static UserRepository userRepository;

    @Autowired
    public UserDeserializer(UserRepository userRepository){
        UserDeserializer.userRepository = userRepository;
    }

    public UserDeserializer(){

    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        if(jsonParser == null){
            return null;
        }
        String clientId = jsonParser.getValueAsString();
        if(StringUtils.isBlank(clientId)){
            return null;
        }
        return userRepository.findById(clientId).get();
    }
}
