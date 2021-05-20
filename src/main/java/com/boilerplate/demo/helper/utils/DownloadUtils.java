package com.boilerplate.demo.helper.utils;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;

public class DownloadUtils {

    public static ResponseEntity<?> download(String fileName, MediaType contentType, byte[] content) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        responseHeaders.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "name");
        responseHeaders.set("name", fileName);
        ResponseEntity.BodyBuilder response = ResponseEntity.ok();
        response.contentType(contentType);
        response.headers(responseHeaders);
        return response.body(new ByteArrayResource(content));
    }
    
    public static ResponseEntity<?> download(String fileName, MediaType contentType, java.io.File content) throws FileNotFoundException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        responseHeaders.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "name");
        responseHeaders.set("name", fileName);
        ResponseEntity.BodyBuilder response = ResponseEntity.ok();
        response.contentType(contentType);
        response.headers(responseHeaders);
        return response.body(new CleanupInputStreamResource(content, true));
    }
}
