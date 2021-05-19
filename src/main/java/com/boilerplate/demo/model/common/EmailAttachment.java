package com.boilerplate.demo.model.common;

import javax.ws.rs.core.MediaType;

public class EmailAttachment {

    private String name;
    private MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM_TYPE;
    private byte[] data = new byte[0];

    public String getName() {
        return name;
    }

    public EmailAttachment setName(String name) {
        this.name = name;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public EmailAttachment setData(byte[] data) {
        this.data = data;
        return this;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public EmailAttachment setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }
}
