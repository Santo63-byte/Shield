// Base interface of public api
package com.sbyte.shield.view;

import org.springframework.web.bind.annotation.RequestBody;

import com.sbyte.shield.modals.AuthVerifierModal;
import com.sbyte.shield.modals.LoginModal;
import com.sbyte.shield.modals.RegisterUpdateModal;
import com.sbyte.shield.modals.RegistrationModal;
import com.sbyte.shield.modals.RegistrationResponseModal;
import com.sbyte.shield.modals.ShieldAuthResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface ShieldView {

    ShieldAuthResponse login(LoginModal loginModal, HttpServletResponse httpServletResponse);

    ShieldAuthResponse logout(HttpServletResponse httpServletResponse, @RequestBody(required = false) Boolean forceLogout);

    RegistrationResponseModal register(RegistrationModal registerModal);

    RegistrationResponseModal updateRegistration(String userId, RegisterUpdateModal registerUpdateModal);

    AuthVerifierModal isAuthenticated(HttpServletResponse httpServletResponse);
}
