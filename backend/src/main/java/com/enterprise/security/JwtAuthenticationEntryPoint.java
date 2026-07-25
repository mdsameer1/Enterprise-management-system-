package com.enterprise.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom JWT Authentication Entry Point
 * 
 * Handles authentication errors when JWT is invalid or missing
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse,
                        AuthenticationException e) throws IOException, ServletException {

        log.error("Responding with unauthorized error. Message - {}", e.getMessage());

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getWriter().write("{" +
                "\"timestamp\":\"" + System.currentTimeMillis() + "\"" +
                ",\"status\":401" +
                ",\"error\":\"Unauthorized\"" +
                ",\"message\":\"" + e.getMessage() + "\"" +
                ",\"path\":\"" + httpServletRequest.getServletPath() + "\"" +
                "}");
    }
}