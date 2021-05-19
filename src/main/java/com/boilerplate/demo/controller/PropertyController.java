package com.boilerplate.demo.controller;

import com.boilerplate.demo.common.RestResources;
import com.boilerplate.demo.domain.model.property.Property;
import com.boilerplate.demo.model.common.ListingType;
import com.boilerplate.demo.model.common.PaginableSortableResponse;
import com.boilerplate.demo.model.common.SortedDirection;
import com.boilerplate.demo.model.property.PropertyFilter;
import com.boilerplate.demo.model.property.PropertyRequest;
import com.boilerplate.demo.service.property.PropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @ApiOperation(value = "Retrieves the list of properties rates", response = Property.class, responseContainer = "List")
    @GetMapping(value = RestResources.PROPERTY_GET_SEARCH)
    public ResponseEntity<?> getProperties(
                                       @ApiParam(
                                               name =  "query",
                                               type = "String",
                                               value = "Use to make a like %...% search on property_name or description")
                                       @RequestParam(required = false) final String query,


                                       @ApiParam(
                                               name =  "pageNr",
                                               defaultValue = "0")
                                       @RequestParam(required = false) final Integer pageNr,

                                       @ApiParam(
                                               name =  "pageSize",
                                               defaultValue = "500")
                                       @RequestParam(required = false) final Integer pageSize,

                                       @ApiParam(
                                               name =  "sortedDirection",
                                               format = "ASC or DESC case sensitive",
                                               defaultValue = "DESC")
                                       @RequestParam(required = false) final SortedDirection sortedDirection,

                                       @ApiParam(
                                               name =  "listingType",
                                               format = "APPROVED or PENDING or ALL case sensitive",
                                               defaultValue = "true")
                                       @RequestParam(required = false) final ListingType listingType) throws IOException {



        PropertyFilter filter = new PropertyFilter()
                .setQuery(query)
                .setListingType(listingType)
                .setPageNr(pageNr)
                .setPageSize(pageSize)
                .setSortedDirection(sortedDirection);

        PaginableSortableResponse<Property> res = propertyService.getProperties(filter);

        if (pageNr == null) {
            return ResponseEntity.ok(res.getItems());
        } else {
            return ResponseEntity.ok(res);
        }

    }
}
