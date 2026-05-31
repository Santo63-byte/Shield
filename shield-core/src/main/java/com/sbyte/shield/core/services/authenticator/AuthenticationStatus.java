package com.sbyte.shield.core.services.authenticator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

@Getter
@Setter
public class AuthenticationStatus  {

    private final Authentication authentication;
    private final boolean authenticated;

    public AuthenticationStatus(Authentication authentication, boolean authenticated) {
        this.authentication = authentication;
        this.authenticated = authenticated;
    }
}
