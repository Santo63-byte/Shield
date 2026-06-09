package com.sbyte.shield.core.services.authenticator.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.dto.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;

@Component("authSupport")
public class AuthSupport {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    HttpServletRequest request;

    public Authentication getAuthenticationStatus() throws ShieldExceptions {
        Authentication authentication = jwtTokenProvider.getAuthenticationFromContext();
        assert authentication != null;
        if(authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
            throw new ShieldExceptions("Anonymous User","No authenticated user found in the current context",401);
        }
        return authentication;
    }
    public UserSessionDTO getCurrentUserSession() {
        UserSessionDTO userssn = new UserSessionDTO();
        userssn.setLoggedInUserId(getAuthenticationStatus().getName());
        userssn.setSessionId(jwtTokenProvider.getSessionIdFromRequest());
        userssn.setAccessToken(jwtTokenProvider.extractTokenFromRequest(request));
        return userssn;
    }
}
