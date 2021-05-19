package com.boilerplate.demo.service.property;

import com.boilerplate.demo.domain.model.property.Property;
import com.boilerplate.demo.model.property.PropertyRequest;
import com.boilerplate.demo.repository.property.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    private PropertyRepository propertyRepository;


    public Property createProperty(PropertyRequest request) throws Exception {
        Property property = new Property();
        property.setPropertyName(request.getPropertyName());
        property.setDescription(request.getDescription());
        property.setLocation(request.getLocation());
        return propertyRepository.save(property);
    }
}
