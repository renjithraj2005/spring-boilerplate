package com.boilerplate.demo.interceptor;

import com.boilerplate.demo.model.common.EventPayload;
import com.boilerplate.demo.security.CustomRequestLoggingKey;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


public abstract class AbstractHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AbstractHandlerInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            return internalPreHandle(request, response, handler);
        } catch (Exception e) {

            try {
                EventPayload eventPayload = new EventPayload();
                eventPayload.put("timestamp", new Date());
                if (request.getUserPrincipal() != null) {
                    eventPayload.put("user", request.getUserPrincipal().getName());
                }
                eventPayload.put("server", request.getServerName());
                eventPayload.put("path", request.getRequestURI());
                eventPayload.put("message", e.getMessage());
                Object requestBody = request.getAttribute(CustomRequestLoggingKey.request_data.keyText());
                if (requestBody != null) {
                    eventPayload.put(CustomRequestLoggingKey.request_data.keyText(), requestBody);
                }
                eventPayload.put("trace", ExceptionUtils.getStackTrace(e));
            } catch (Exception ex1) {
                // swallow
                logger.warn("Couldn't send notification on error in interceptor", logger);
            }

            throw e;
        }
    }

    protected abstract boolean internalPreHandle(HttpServletRequest request, HttpServletResponse response,
                                                 Object handler) throws Exception;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
