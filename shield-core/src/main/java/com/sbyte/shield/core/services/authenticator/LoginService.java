package com.sbyte.shield.core.services.authenticator;

import com.sbyte.shield.core.base.impl.CoreServiceBase;
import com.sbyte.shield.core.base.impl.CredentialSupport;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.core.services.authenticator.support.JwtTokenProvider;
import com.sbyte.shield.core.services.authenticator.validators.AuthorizationValidator;
import com.sbyte.shield.datasource.mybatis.UserRepository;
import com.sbyte.shield.datasource.storage.TokenStorage;
import com.sbyte.shield.dto.*;
import com.sbyte.shield.modals.IndicatorsModal;
import com.sbyte.shield.modals.ShieldAuthResponse;
import com.sbyte.shield.modals.UserSession;
import com.sbyte.shield.utils.ShieldUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("loginService")
public class LoginService extends CoreServiceBase<CredentialsDTO, ShieldAuthResponse> {

    @Autowired
    private CredentialSupport credentialSupport;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private TokenStorage tokenStorage;

    @Autowired
    private AuthorizationValidator authorizationValidator;

    @Autowired
    private ShieldUtils utils;
    @Autowired
    UserRepository userRepository;


    @Override
    public void validate(CredentialsDTO input) {
        // No specific validation required for login in this implementation
        ShieldErrorsDTO errorsDTO = new ShieldErrorsDTO();
        authorizationValidator.validate(input, errorsDTO);
    }
    @Override
    public void enrichDTO(CredentialsDTO cdto) throws ShieldExceptions {
        ///  device id and sessionid creation
        cdto.setDeviceId(utils.generateDeviceId(cdto.getDevice()));
        if(cdto.getDeviceId().equals("unknown")){
            throw new ShieldExceptions("Device Type Not Found","Device Type of client is not fetched or parsed",400);
        }
        cdto.setSsnid(utils.generateSessionId());
        ///  prev user context setting
        UserFilterDTO userFilterDTO = new UserFilterDTO();
        userFilterDTO.setUserName(cdto.getUserName());
        userFilterDTO.setUserEmail(cdto.getEmail());
        BasicUserCredentialDTO ctx;
        try {
            ctx = userRepository.getUserExistingCredContext(userFilterDTO);
        }  catch (Exception e) {
            throw new ShieldExceptions("Error occured for transaction:SHIELDUSRCONTEXTSEARCH", " Unable to fetch user credential context");
        }
        if (ctx == null) {
            throw new ShieldExceptions("User not found", "User Does not exist");
        }
        cdto.setUserPreviousContextData(ctx);
    }
    @Override
    public ShieldAuthResponse execute(CredentialsDTO input) {
        UsernamePasswordAuthenticationToken tkn;
        try {
            tkn = authenticate(input);
        } catch (AuthenticationException ex) {
            throw new ShieldExceptions("Authentication Failed", ex.getMessage());
        }
        ShieldAuthResponse response = new ShieldAuthResponse();
        if(tkn != null) {
            // session creation and JWT generation
            buildJWTResponse(input, response);
            Cookie cookie = persistAndGetCookie(input,
                    jwtTokenProvider.getExpiresInSeconds() * 1000L, response);
            HttpServletResponse httpServletResponse =  input.getHttpcontextResponse();
            if(httpServletResponse != null) {
                httpServletResponse.addCookie(cookie);
            }
            return response; /// Success case
        }
        response.setStatus("AUTH_FAILED");
        return response; /// Failure case
    }
    public UsernamePasswordAuthenticationToken authenticate(CredentialsDTO authentication) throws AuthenticationException {
        if (authentication == null) {
            throw new BadCredentialsException("Empty authentication payload");
        }
        BasicUserCredentialDTO ctx = authentication.getUserPreviousContextData();
        if (ctx == null) {
            throw new BadCredentialsException("Missing previous credential context");
        }

        String providedUsername = authentication.getUserName();
        String providedEmail = authentication.getEmail();
        CharSequence providedPassword = authentication.getPassword();
        String storedUsername = ctx.getUserName();
        String storedEmail = ctx.getEmail();
        String storedHashedPwd = ctx.getPassword();
        boolean usernameMatches = storedUsername != null && storedUsername.equals(providedUsername);
        boolean emailMatches = storedEmail != null && storedEmail.equals(providedEmail);
        if ((usernameMatches || emailMatches) &&
                (credentialSupport.shieldEncoders.passwordEncoder().matches(providedPassword, storedHashedPwd))) {
            return new UsernamePasswordAuthenticationToken(
                    providedUsername,
                    null,
                    Collections.emptyList()
            );
        }
        throw new BadCredentialsException("Credential mismatch error for user " + providedUsername);
    }
    private void  buildJWTResponse( CredentialsDTO usercredentials, ShieldAuthResponse response) {
        String jwttoken = jwtTokenProvider.generateToken(usercredentials);
        long expirySeconds = jwtTokenProvider.getExpiresInSeconds();
        long expiryMillis = expirySeconds * 1000L;
        populateJWTResponse(usercredentials.getUserName(), jwttoken, expiryMillis, response);
    }
    private void storeRefreshToken(String sessionId,String refreshToken, Long expiryMillis, String userName) throws ShieldExceptions {
        RFTokenDTO rfTokenDTO = new RFTokenDTO();
        rfTokenDTO.setTokenid(System.currentTimeMillis());
        rfTokenDTO.setRefreshToken(refreshToken);
        rfTokenDTO.setExpiresAt(expiryMillis);
        rfTokenDTO.setLoggedInUserId(userName);
        rfTokenDTO.setSessionId(sessionId);
        try {
            tokenStorage.persistToken(rfTokenDTO);
        }
        catch (Exception e){
            throw new ShieldExceptions("Internal Exception " + e.getMessage());
        }
    }
    private Cookie persistAndGetCookie(CredentialsDTO input, Long expiryMillis, ShieldAuthResponse response) {
        String refreshToken = jwtTokenProvider.generateRefreshToken(input.getUserName());
        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(expiryMillis.intValue() / 1000);
        storeRefreshToken(input.getSsnid(),refreshToken, expiryMillis, response.getUserSession().getUsername());
        refreshToken = null; // setting to null after cookie creation
        return refreshCookie;
    }
    private void populateJWTResponse(String username, String token, Long expiryMillis, ShieldAuthResponse response) {
        response.setStatus("SUCCESS");
        response.setBearerToken("Bearer " + token);
        UserSession userSession = new UserSession();
        userSession.setExpiryTimestamp(expiryMillis);
        userSession.setUsername(username);
        response.setUserSession(userSession);
        IndicatorsModal indicators = new IndicatorsModal();
        indicators.setVerified(true);
        indicators.setActive(true);
        response.setIndicators(indicators);
    }
}
