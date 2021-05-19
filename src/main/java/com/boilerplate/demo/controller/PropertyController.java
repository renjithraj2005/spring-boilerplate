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
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> createComment(@RequestBody PropertyRequest propertyRequest) throws Exception {
        Property chat = propertyService.createProperty(propertyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(chat);
    }
}
