package com.sbyte.shield.api;

import com.sbyte.shield.modals.*;
import org.springframework.http.ResponseEntity;

/**
 * Shield's API interface.
 * @Author: Santo
 */

public interface ShieldAPI {

    /**
     * Function to handle user login.
     *
     * @param loginModal the login modal containing user credentials
     * @return a response indicating the result of the login operation
     */
    ShieldAuthResponse login(LoginModal loginModal);
    /**
     *
     * Function to handle user logout.
     *
     *
     * @return a response indicating the result of the logout operation
     */
    ShieldAuthResponse logout(Boolean forceLogout);

    /**
     * Function to handle user registration.
     *
     * @param registerModal the registration modal containing user details
     * @return a response indicating the result of the registration operation
     */
    RegistrationResponseModal register(RegistrationModal registerModal);

    /**
     * Function to update user registration details.
     *
     * @param registerUpdateModal the registration update modal containing updated user details
     * @return a response indicating the result of the registration update operation
     */

    RegistrationResponseModal updateRegistration(String userId, RegisterUpdateModal registerUpdateModal);

    /**
     * Function to verify if the current session is authenticated.
     *
     * @return an authentication verifier modal indicating the authentication status
     */
    AuthVerifierModal isAuthenticated();

    /**
     * Function to refresh authentication token.
     *
     * @param authModal the authenticator modal containing necessary details
     * @return a response indicating the result of the token refresh operation
     */

    // ShieldResponse refreshToken(AuthenticatorModal authModal);


}