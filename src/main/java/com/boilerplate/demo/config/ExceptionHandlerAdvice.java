package com.boilerplate.demo.config;

import com.boilerplate.demo.common.RestResources;
import com.boilerplate.demo.model.common.CustomException;
import com.boilerplate.demo.model.common.CustomResponse;
import com.boilerplate.demo.model.common.EventPayload;
import com.boilerplate.demo.security.CustomRequestLoggingKey;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice extends DefaultErrorAttributes {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);



	@ExceptionHandler(CustomException.class)
	public ResponseEntity<CustomResponse> handleException(HttpServletResponse response, CustomException exp) throws Exception {
		return new ResponseEntity<>(CustomResponse.builder().message(exp.getMessage()), exp.getHttpStatus());
	}

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, true);
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		errorAttributes.put("server", request.getServerName());
		try{
			if (request.getUserPrincipal() != null) {
				errorAttributes.put("user", request.getUserPrincipal().getName());
			}
		} catch (Exception exp){
			logger.warn("Cannot retrieve current user: {}", exp.getMessage());
		}
		Object trace = errorAttributes.get("trace");
		if (trace != null) {
			logger.error("Exception: {}", trace);
		}

		if (!silentException(errorAttributes)) {
			EventPayload eventPayload = new EventPayload(errorAttributes);

			Object requestBody = webRequest.getAttribute(CustomRequestLoggingKey.request_data.keyText(), RequestAttributes.SCOPE_REQUEST);

			if (requestBody != null) {
				eventPayload.put(CustomRequestLoggingKey.request_data.keyText(), requestBody);
			}
		}

		errorAttributes.remove("user");
		errorAttributes.remove("trace");
		return errorAttributes;
	}

	private boolean silentException(Map<String, Object> errorAttributes) {
		if (MapUtils.isEmpty(errorAttributes)) {
			return false;
		}
		String path = String.valueOf(errorAttributes.get("path"));
		String status = String.valueOf(errorAttributes.get("status"));
		String error = String.valueOf(errorAttributes.get("error"));
		String message = String.valueOf(errorAttributes.get("message"));

		boolean silent = false;

		// case 1: don't spam slack with login failed exception
		silent |= (String.valueOf(HttpStatus.UNAUTHORIZED.value()).equals(status) &&
				path != null && path.endsWith(RestResources.USER_POST_LOGIN));

		// case 2: user logged out already, but still able to use the frontend --> clientId missing
		silent |= (String.valueOf(HttpStatus.BAD_REQUEST.value()).equals(status)
				&& "Bad Request".equals(error)
				&& "Required String parameter 'clientId' is not present".equals(message));

		return silent;
	}


}
