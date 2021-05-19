package com.boilerplate.demo.controller;

import com.boilerplate.demo.common.RestResources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PropertyControllerIntegrationTest extends AbstractControllerTest{

    @BeforeEach
    public void setUp() throws IOException {
        super.setup();
    }

    @Test
    @Order(1)
    public void test_PropertyControllerFlow() throws Exception {
        String id = uploadProperty();
        updateProperty(id);
        approveProperty(id);
        propertySearch();
    }

    private String  uploadProperty() throws Exception {
        MvcResult result =  mockMvc.perform(post((RestResources.PROPERTY_ROOT + RestResources.PROPERTY_POST_SAVE))
                .content("{\"description\":\"test description\",\"location\":\"test location\",\"propertyName\":\"test property\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(user(client1())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();

        Map<String, Object> matches = payloadToMap(responsePayload(result));
        return matches.get("id").toString();

    }

    private void updateProperty(String id) throws Exception {
        mockMvc.perform(put((RestResources.PROPERTY_ROOT + RestResources.PROPERTY_PUT_UPDATE).replace("{id}", id))
                .content("{\"description\":\"updated description\",\"location\":\"test location\",\"propertyName\":\"test property\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(user(client1())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("updated description")));
    }

    private void approveProperty(String id) throws Exception {
        mockMvc.perform(put((RestResources.PROPERTY_ROOT + RestResources.PROPERTY_PUT_APPROVE).replace("{id}", id))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(user(client1())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.approved", is(true)));
    }

    private void propertySearch() throws Exception {
        mockMvc.perform(get(RestResources.PROPERTY_ROOT + RestResources.PROPERTY_GET_SEARCH)
                .param(P_listingType, "APPROVED")
                .param(P_pageNr, "0")
                .param(P_pageSize, "10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(user(client1())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRecords", is(1)));

        mockMvc.perform(get(RestResources.PROPERTY_ROOT + RestResources.PROPERTY_GET_SEARCH)
                .param(P_listingType, "PENDING")
                .param(P_pageNr, "0")
                .param(P_pageSize, "10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(user(client1())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRecords", is(0)));

        mockMvc.perform(get(RestResources.PROPERTY_ROOT + RestResources.PROPERTY_GET_SEARCH)
                .param(P_listingType, "ALL")
                .param(P_pageNr, "0")
                .param(P_pageSize, "10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(user(client1())))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalRecords", is(1)));
    }
}
