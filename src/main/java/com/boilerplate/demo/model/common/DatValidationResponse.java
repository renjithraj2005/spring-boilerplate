package com.boilerplate.demo.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DatValidationResponse {

    private String clientId;
    private Long datId;
    private boolean valid;
    private XsdValidationResult header = new XsdValidationResult();
    private XsdValidationResult body = new XsdValidationResult();

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getDatId() {
        return datId;
    }

    public void setDatId(Long datId) {
        this.datId = datId;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public XsdValidationResult getHeader() {
        return header;
    }

    public void setHeader(XsdValidationResult header) {
        this.header = header;
    }

    public XsdValidationResult getBody() {
        return body;
    }

    public void setBody(XsdValidationResult body) {
        this.body = body;
    }
}
