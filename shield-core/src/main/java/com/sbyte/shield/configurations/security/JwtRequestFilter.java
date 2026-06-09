package com.sbyte.shield.configurations.security;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.core.services.authenticator.support.JwtTokenProvider;
import com.sbyte.shield.dto.ShieldErrorsDTO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.extractTokenFromRequest(request);
        
        try {
            if(token != null && jwtTokenProvider.isValidToken(token)) {
                String username = jwtTokenProvider.getUsernameFromToken(token);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,           // Principal
                                null,               // Credentials
                                Collections.emptyList()  // Authorities/Roles --> not impntd will do in future
                        );
                authentication.setDetails(new org.springframework.security.web.authentication.WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (ShieldExceptions ex) {
            sendErrorResponse(response, ex);
        } catch (ServletException | IOException e) {
            throw new ServletException(e);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, ShieldExceptions ex) throws IOException {
        ShieldErrorsDTO errorDetails = ex.getErrorDetails();
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Shield Exception occurred");
        errorResponse.put("code", errorDetails.getCode());
        errorResponse.put("description", errorDetails.getMessage());
        errorResponse.put("status", errorDetails.getStatus());
        
        response.setStatus(errorDetails.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        
        log.warn("Shield exception in request filter: {} - {}", errorDetails.getCode(), errorDetails.getMessage());
    }

}
