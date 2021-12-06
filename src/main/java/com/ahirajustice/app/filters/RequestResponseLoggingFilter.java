package com.ahirajustice.app.filters;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ahirajustice.app.constants.SecurityConstants;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class RequestResponseLoggingFilter extends GenericFilterBean{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Instant startTime = Instant.now();

        logRequest(request);
        chain.doFilter(request, response);

        double processTime = Duration.between(startTime, Instant.now()).getNano() / 1000000;
        logResponse(request, response, processTime);
    }

    private void logRequest(HttpServletRequest request) {
        if (!excludeFromRequestResponseLogger(request.getRequestURI(), request.getMethod())) {
            String msg = String.format("Running request '%s > %s'", request.getMethod(), request.getRequestURI());
            log.info(msg);
        }
    }

    private void logResponse(HttpServletRequest request, HttpServletResponse response, double processTime) {
        if (!excludeFromRequestResponseLogger(request.getRequestURI(), request.getMethod())) {
            String msg = String.format("Finished running request '%s > %s' in %f ms", request.getMethod(), request.getRequestURI(), processTime);
            log.info(msg);

            logger.info(String.format("Response Status Code: %d", response.getStatus()));
        }
    }

    private boolean excludeFromRequestResponseLogger(String requestURI, String requestMethod) {
        for (String url : SecurityConstants.EXCLUDE_FROM_REQUEST_RESPONSE_LOGGER) {
            String excludeURI = url.split(", ")[0];
            String excludeMethod = url.split(", ")[1];

            if (excludeURI.equals(requestURI) && excludeMethod.equals(requestMethod)){
                return true;
            }

            if (excludeURI.endsWith("/**") && requestURI.startsWith(excludeURI.replace("/**", "")) && excludeMethod.equals(requestMethod)){
                return true;
            }
        }

        return false;
    }
    
}
