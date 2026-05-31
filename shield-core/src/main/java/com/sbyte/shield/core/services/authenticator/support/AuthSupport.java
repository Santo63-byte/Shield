package com.sbyte.shield.core.services.authenticator.support;

import com.sbyte.shield.dto.UserSessionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("authSupport")
public class AuthSupport {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    HttpServletRequest request;

    public Authentication getAuthenticationStatus() {
        Authentication authentication = jwtTokenProvider.getAuthenticationFromContext();
        assert authentication != null;
        authentication.setAuthenticated(authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken));
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
