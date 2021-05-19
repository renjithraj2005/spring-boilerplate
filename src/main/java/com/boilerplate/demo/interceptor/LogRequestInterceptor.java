package com.boilerplate.demo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogRequestInterceptor extends AbstractHandlerInterceptor {

    @Override
    protected boolean internalPreHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        HttpServletRequest requestCacheWrapperObject = new ContentCachingRequestWrapper(request);
        requestCacheWrapperObject.getParameterMap(); // <-- required to make sure the request body get copied
        // Read inputStream from requestCacheWrapperObject and log it
        return true;
    }

}