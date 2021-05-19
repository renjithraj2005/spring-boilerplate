package com.boilerplate.demo.security.oauth;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.boilerplate.demo.common.RestResources;
import org.apache.catalina.connector.RequestFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@Component
@Order(value = Integer.MIN_VALUE)
public class OAuthRequestMapperFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(Filter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	
    }
    @SuppressWarnings("unchecked")
	@Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException,ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        if (!request.getServletPath().contains(RestResources.USER_POST_LOGIN)) {
            chain.doFilter(request, response);
            return;
        }
        if(request.getContentType() == null || !request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)){
            logger.error("Content Type must of Json type");
            chain.doFilter(request, response);
            return;
        }
        InputStream is = request.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] json = buffer.toByteArray();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
        HashMap<String, String> result = objectMapper.readValue(json, HashMap.class);
        HashMap<String, String[]> r = new HashMap<>();
        for (String key : result.keySet()) {
            String[] val = new String[1];
            val[0] = result.get(key);
            r.put(key, val);
        }
        String[] val = new String[1];
        val[0] = ((RequestFacade) request).getMethod();
        r.put("_method", val);
        HttpServletRequest s = new CustomRequestWrapper(((HttpServletRequest) request), r);
        chain.doFilter(s, response);
    }

    @Override
    public void destroy() {
    }
}