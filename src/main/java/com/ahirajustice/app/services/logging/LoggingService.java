package com.ahirajustice.app.services.logging;


import com.ahirajustice.app.constants.SecurityConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoggingService implements ILoggingService {


    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {

        if (SecurityConstants.SENSITIVE_ENDPOINTS.contains(httpServletRequest.getRequestURI())) {
            return;
        }

        StringBuilder data = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);

        data.append("REQUEST ");
        data.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        data.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        data.append("requestHeaders=[").append(buildHeadersMap(httpServletRequest)).append("] ");

        if (!parameters.isEmpty()) {
            data.append("parameters=[").append(parameters).append("] ");
        }

        if (body != null) {
            data.append("requestBody=[").append(buildBody(body)).append("] ");
        }

        log.info(data.toString());
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {

        if (SecurityConstants.SENSITIVE_ENDPOINTS.contains(httpServletRequest.getRequestURI())) {
            return;
        }

        StringBuilder data = new StringBuilder();

        data.append("RESPONSE ");
        data.append("method=[").append(httpServletRequest.getMethod()).append("] ");
        data.append("path=[").append(httpServletRequest.getRequestURI()).append("] ");
        data.append("responseHeaders=[").append(buildHeadersMap(httpServletResponse)).append("] ");

        if (body != null) {
            data.append("responseBody=[").append(buildBody(body)).append("] ");
        }

        log.info(data.toString());
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }


    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }


    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();

        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }

    private String buildBody(Object body) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        String jsonInString = null;
        try {
            jsonInString = objectMapper.writer().writeValueAsString(body);
        } catch (JsonProcessingException e) {
            //do nothing
        }

        return jsonInString;
    }

}
