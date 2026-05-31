package com.sbyte.shield.core.services.authenticator.support;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Collections;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException {
        String token = jwtTokenProvider.extractTokenFromRequest(request);
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

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
