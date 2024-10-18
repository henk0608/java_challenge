package com.example;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    //private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);
    private static final String REQUEST_ID_HEADER = "X-Request-ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Generate a unique request ID
        String requestId = UUID.randomUUID().toString();

        // Add it to the request attribute (so it can be used later in controllers, etc.)
        request.setAttribute(REQUEST_ID_HEADER, requestId);

        // Also add it to the response header
        response.setHeader(REQUEST_ID_HEADER, requestId);

        //logger.info("Incoming request with ID: {}", requestId);
        return true;
    }
}
