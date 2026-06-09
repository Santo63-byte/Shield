package com.sbyte.shield.core.services.authenticator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbyte.shield.core.base.impl.CoreServiceBase;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.core.services.authenticator.support.AuthSupport;
import com.sbyte.shield.core.services.authenticator.support.JwtTokenProvider;
import com.sbyte.shield.core.services.authenticator.validators.CurrentSessionValidator;
import com.sbyte.shield.datasource.storage.TokenStorage;
import com.sbyte.shield.dto.CredentialsDTO;
import com.sbyte.shield.dto.ShieldErrorsDTO;
import com.sbyte.shield.dto.UserSessionDTO;
import com.sbyte.shield.modals.ShieldAuthResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Component("tokenRevocationService")
public class TokenRevocationService extends CoreServiceBase<CredentialsDTO, ShieldAuthResponse> {

    @Autowired
    private AuthSupport authSupport;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private TokenStorage tokenStorage;

    @Autowired
    private CurrentSessionValidator currentSessionValidator;


    @Override
    public void validate(CredentialsDTO input) throws ShieldExceptions {
        // No specific validation required for token revocation
        ShieldErrorsDTO errors = new ShieldErrorsDTO();
        currentSessionValidator.validate(input,errors);
    }
    @Override
    public ShieldAuthResponse execute(CredentialsDTO input) throws ShieldExceptions {
            ShieldAuthResponse response = new ShieldAuthResponse();
            if (input.isForcedAction()) {
                invalidateAndBlacklistToken();
            } else {
                revokeTokenWithoutResponse();
            }
            HttpServletResponse httpResponse = input.getHttpcontextResponse();
            if (httpResponse != null) {
                Cookie refreshCookie = new Cookie("refreshToken", null);
                refreshCookie.setHttpOnly(true);
                refreshCookie.setSecure(true);
                refreshCookie.setPath("/");
                refreshCookie.setMaxAge(0); // Expire immediately
                httpResponse.addCookie(refreshCookie);
            }
            response.setStatus("LOGOUT_SUCCESS");
            return response;
    }
    public void revokeTokenWithoutResponse() throws ShieldExceptions {
        UserSessionDTO currentUserSession = authSupport.getCurrentUserSession();
        try{
            tokenStorage.deleteRefreshToken(currentUserSession);
        }
        catch (Exception e){
            throw new ShieldExceptions("Error during token revocation", "TOKEN_REVOCATION_ERROR");
        }
    }
    public void invalidateAndBlacklistToken(){
        ///  This method invalidates the current token and adds it to blacklist store
        UserSessionDTO currentUserSession = authSupport.getCurrentUserSession();
        try {
            // token invalidation from store
            tokenStorage.moveTokentoBlackList(currentUserSession);
        }
        catch (Exception e){
            throw new ShieldExceptions("Error during logout token removal", "TOKEN_REMOVAL_ERROR");
        }
        jwtTokenProvider.clearAuthenticationContext();
    }
}
