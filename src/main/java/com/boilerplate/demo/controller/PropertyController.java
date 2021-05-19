package com.boilerplate.demo.controller;

import com.boilerplate.demo.common.RestResources;
import com.boilerplate.demo.domain.model.property.Property;
import com.boilerplate.demo.model.property.PropertyRequest;
import com.boilerplate.demo.service.property.PropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestResources.PROPERTY_ROOT)
@Api(tags = "properties")
public class PropertyController {

    private final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    @Autowired
    private PropertyService propertyService;

    @PostMapping(value = RestResources.PROPERTY_POST_SAVE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create a Chat")
    public ResponseEntity<?> createProperty(@RequestBody PropertyRequest propertyRequest) throws Exception {
        Property property = propertyService.createProperty(propertyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(property);
    }

    @PutMapping(value = RestResources.PROPERTY_PUT_UPDATE)
    @ApiOperation(value = "Update Existing Property")
    public ResponseEntity<?> updateProperty(@PathVariable Long id,
                                                       @RequestBody PropertyRequest propertyRequest) throws Exception{
        Property property = propertyService.updateProperty(propertyRequest,id);
        return ResponseEntity.ok().body(property);
    }

    @PutMapping(value = RestResources.PROPERTY_PUT_APPROVE)
    @ApiOperation(value = "Approve Existing Property")
    public ResponseEntity<?> approveProperty(@PathVariable Long id) throws Exception{
        Property property = propertyService.approveProperty(id);
        return ResponseEntity.ok().body(property);
    }
}
