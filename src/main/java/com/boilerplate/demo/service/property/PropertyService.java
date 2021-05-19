package com.boilerplate.demo.service.property;

import com.boilerplate.demo.domain.model.property.Property;
import com.boilerplate.demo.domain.specification.PropertySpecification;
import com.boilerplate.demo.model.common.CustomException;
import com.boilerplate.demo.model.common.PaginableSortableResponse;
import com.boilerplate.demo.model.property.PropertyFilter;
import com.boilerplate.demo.model.property.PropertyRequest;
import com.boilerplate.demo.repository.property.PropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    @Autowired
    private PropertyRepository propertyRepository;


    public Property createProperty(PropertyRequest request) {
        Property property = new Property();
        property.setPropertyName(request.getPropertyName());
        property.setDescription(request.getDescription());
        property.setLocation(request.getLocation());
        return propertyRepository.save(property);
    }

    public Property updateProperty(PropertyRequest request,final Long propertyId) throws CustomException {
        Optional<Property> existingProperty = propertyRepository.findById(propertyId);
        if(!existingProperty.isPresent()){
            throw new CustomException(HttpStatus.BAD_REQUEST).message("Invalid property id");
        }
        Property property = existingProperty.get();
        property.setPropertyName(request.getPropertyName());
        property.setDescription(request.getDescription());
        property.setLocation(request.getLocation());
        return propertyRepository.save(property);
    }

    public Property approveProperty(final Long propertyId) throws CustomException {
        Optional<Property> existingProperty = propertyRepository.findById(propertyId);
        if(!existingProperty.isPresent()){
            throw new CustomException(HttpStatus.BAD_REQUEST).message("Invalid property id");
        }
        Property property = existingProperty.get();
        property.setApproved(true);
        return propertyRepository.save(property);
    }

    public PaginableSortableResponse<Property> getProperties(PropertyFilter filter) {
        PropertySpecification specification = new PropertySpecification(filter);
        PageRequest pageRequest = specification.buildPageRequest();

        Page<Property> result = propertyRepository.findAll(specification, pageRequest);
        PaginableSortableResponse<Property> response = new PaginableSortableResponse<>(result, filter);

        return response;
    }

}
