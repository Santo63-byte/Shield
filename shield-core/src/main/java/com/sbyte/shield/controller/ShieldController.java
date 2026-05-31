
package com.sbyte.shield.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbyte.shield.api.ShieldAPI;
import com.sbyte.shield.modals.AuthVerifierModal;
import com.sbyte.shield.modals.LoginModal;
import com.sbyte.shield.modals.RegisterUpdateModal;
import com.sbyte.shield.modals.RegistrationModal;
import com.sbyte.shield.modals.RegistrationResponseModal;
import com.sbyte.shield.modals.ShieldAuthResponse;
import com.sbyte.shield.view.ShieldView;

import jakarta.servlet.http.HttpServletResponse;

/**
 * ShieldController handles all the shield view services.
 * It implements the ShieldView interface to provide the necessary functionalities.
 * This controller delegates the actual processing to the Shield's API interface.
 * @version 0.1
 * @Author: Santo
 *
 */

@RestController
@RequestMapping("/api/shield")
public class ShieldController implements ShieldView {

    @Autowired
    private ShieldAPI shieldAPI;

    @PostMapping("/login")
    @Override
    public ShieldAuthResponse login(@RequestBody LoginModal loginModal,
                                                    HttpServletResponse httpServletResponse) {
        loginModal.setHttpContextResponse(httpServletResponse);
        return shieldAPI.login(loginModal);
    }

    @PostMapping("/logout/{forceLogout}")
    @Override
    public ShieldAuthResponse logout(HttpServletResponse httpServletResponse, @PathVariable(required = false) Boolean forceLogout) {
        return shieldAPI.logout(forceLogout);
    }

    @PostMapping("/register")
    @Override
    public RegistrationResponseModal register(@RequestBody RegistrationModal registerModal) {
        return shieldAPI.register(registerModal);
    }

    @PutMapping("/register/update/{userId}")
    @Override
    public RegistrationResponseModal updateRegistration(@PathVariable String userId, RegisterUpdateModal registerUpdateModal){
        return shieldAPI.updateRegistration(userId,registerUpdateModal);
    }

    @PostMapping("/isAuthenticated")
    @Override
    public AuthVerifierModal isAuthenticated(HttpServletResponse httpServletResponse) {
        return shieldAPI.isAuthenticated();
    }
}
