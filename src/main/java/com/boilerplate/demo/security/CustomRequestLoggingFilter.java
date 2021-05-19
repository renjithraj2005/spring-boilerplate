package com.boilerplate.demo.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class CustomRequestLoggingFilter extends CommonsRequestLoggingFilter {

    private static final Set<String> MASKED_HEADERS = new HashSet<>(Arrays.asList("authorization"));

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return true;
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        // ignore
    }

    /**
     * masked when this Predicate.test() --> false
     * @return
     */
    @Override
    public Predicate<String> getHeaderPredicate() { // allow this header in log?
        return (String headerName) -> !MASKED_HEADERS.contains(StringUtils.lowerCase(headerName));
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        if (StringUtils.isNotBlank(message)) {
            request.setAttribute(CustomRequestLoggingKey.request_data.keyText(), message);
        }
    }
}
