package com.boilerplate.demo.security;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AccessDeniedException authException) throws IOException {
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
        JSONObject response = new JSONObject();
        response.put("timestamp", new Date().getTime());
        response.put("message", "Access Denied");
        httpResponse.getWriter().write(response.toString());
    }
}