package com.sbyte.shield.core;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.core.services.authenticator.Authenticator;
import com.sbyte.shield.core.services.register.Register;
import com.sbyte.shield.core.services.register.RegisterUpdate;
import com.sbyte.shield.dto.CredentialsDTO;
import com.sbyte.shield.dto.RegistrationDTO;
import com.sbyte.shield.modals.AuthVerifierModal;
import com.sbyte.shield.modals.RegistrationResponseModal;
import com.sbyte.shield.modals.ShieldAuthResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("shieldCore")
public class ShieldCore {

    @Autowired
    private Register userRegister;

    @Autowired
    private Authenticator authenticator;

    @Autowired
    private RegisterUpdate registerUpdate;

    public ShieldAuthResponse performLogin(CredentialsDTO loginModal) {
        return authenticator.authorize(loginModal);
    }

    public ShieldAuthResponse performLogout(Boolean forceLogout) throws ShieldExceptions {
        if(verifyCurrentSession().isAuthenticated()) {
            CredentialsDTO logoutModal = new CredentialsDTO();
            if(forceLogout != null){
                logoutModal.setForcedAction(forceLogout);
            }
            return authenticator.revoke(logoutModal);
        }
        throw new ShieldExceptions("NOT_AUTHENTICATED","USER SESSION NOT AUTHENTICATED",401);
    }

    public RegistrationResponseModal performRegistration(RegistrationDTO registrationDTO) throws ShieldExceptions {
        return userRegister.fire(registrationDTO);
    }

    public AuthVerifierModal verifyCurrentSession(){
        return authenticator.verifySession();
    }

    public void invalidateAndBlacklistToken(){
        authenticator.invalidateAndBlacklistToken();
    }
    public RegistrationResponseModal updateRegistrationDetails(RegistrationDTO registrationDTO) throws ShieldExceptions {
        if(verifyCurrentSession().isAuthenticated()) {
            return registerUpdate.fire(registrationDTO);
        }
        throw new ShieldExceptions("NOT_AUTHENTICATED","USER SESSION NOT AUTHENTICATED",401);
    }
    public void resetPassword(RegistrationDTO registrationDTO) throws ShieldExceptions {
        throw new ShieldExceptions("Not Implemented","Password reset is not implemented yet",501);
    }
    public void deleteAccount(String userId, String service) throws ShieldExceptions {
        throw new ShieldExceptions("Not Implemented","User deletion is not implemented yet",501);
    }
}