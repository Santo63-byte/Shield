package com.sbyte.shield.core.services.authenticator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sbyte.shield.core.base.impl.CredentialSupport;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.core.services.authenticator.support.AuthSupport;
import com.sbyte.shield.core.services.authenticator.support.JwtTokenProvider;
import com.sbyte.shield.core.services.authenticator.validators.AuthorizationValidator;
import com.sbyte.shield.datasource.storage.TokenStorage;
import com.sbyte.shield.dto.CredentialsDTO;
import com.sbyte.shield.modals.AuthVerifierModal;
import com.sbyte.shield.modals.ShieldAuthResponse;

import lombok.extern.slf4j.Slf4j;

///JWT based authenticator implementation
@Slf4j
@Component("authenticator")
public class Authenticator  {

    @Autowired
    AuthorizationValidator authorizationValidator;

    @Autowired
    CredentialSupport credentialSupport;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    TokenStorage tokenStorage;

    @Autowired
    private LoginService  loginService;

    @Autowired
    private TokenRevocationService tokenRevocationService;

    @Autowired
    private AuthSupport authSupport;


    public ShieldAuthResponse authorize(CredentialsDTO input) throws ShieldExceptions {
        log.info("Authorizing user session: {}", input.getSsnid());
        return loginService.fire(input);
    }
    public ShieldAuthResponse revoke(CredentialsDTO input) throws ShieldExceptions {
            log.info("Revoking token for user session: {}", input.getSsnid());
            return tokenRevocationService.fire(input);
    }

    public AuthVerifierModal verifySession(){
        Authentication sessionStatus = authSupport.getAuthenticationStatus();
        boolean authenticated = sessionStatus.isAuthenticated();
        String userName = sessionStatus.getName();
        if(!authenticated){
            return new AuthVerifierModal() {{setAuthenticated(false);}};
        }
        AuthVerifierModal authVerifierModal = new AuthVerifierModal();
        authVerifierModal.setAuthenticated(true);
        authVerifierModal.setUserName(userName);
        return authVerifierModal;
    }
    public void invalidateAndBlacklistToken(){
        log.warn("Invalidating and blacklisting token for current session");
        tokenRevocationService.invalidateAndBlacklistToken();
    }
}
