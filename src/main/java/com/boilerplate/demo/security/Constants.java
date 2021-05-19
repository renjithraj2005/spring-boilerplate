package com.boilerplate.demo.security;

public class Constants {
    public static final int MAX_REQUEST_DATA_LENGTH_TO_LOG = 20000;
    public static final int MAX_LOGIN_RETRY_COUNT = 3;
    public static final String[] WEB_IGNORE = {
        "/swagger-resources/**",
        "//swagger-resources/configuration/**",
        "/swagger-ui.html",
        "/swagger-ui.html/**",
        "/v2/api-docs",
        "/webjars/**",
        "/actuator/**",
        "/**.html",
        "/configuration/**"
    };
}
