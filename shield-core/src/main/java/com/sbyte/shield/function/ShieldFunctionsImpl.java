package com.sbyte.shield.function;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.dto.mapper.ShieldMapper;
import com.sbyte.shield.modals.*;
import com.sbyte.shield.api.ShieldAPI;
import com.sbyte.shield.core.ShieldCore;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service("shieldFunctions")
public class ShieldFunctionsImpl  implements ShieldAPI  {

    @Autowired
    ShieldMapper shieldMapper;

    @Autowired
    private ShieldCore shieldCore;

    @Override
    public ShieldAuthResponse login(LoginModal loginModal) throws ShieldExceptions {
        ShieldAuthResponse authResponse = shieldCore.performLogin(shieldMapper.convertLoginModaltoDTO(loginModal));
        if (authResponse == null) {
                log.error("Login Failure::Auth Response is empty / null , suspecting server internal error or client side manipulation");
                throw new ShieldExceptions("Login Failed","Authentication process failed due to internal server error",500);
        }
        return authResponse;
    }

    @Override
    public ShieldAuthResponse logout(Boolean forceLogout) throws  ShieldExceptions {
        ShieldAuthResponse authResponse = shieldCore.performLogout(forceLogout);
        if(authResponse == null ){
            log.error("Logout Failure::Auth Response is empty / null , suspecting server internal error or client side manipulation");
            shieldCore.invalidateAndBlacklistToken(); /// if any token created it  will be invalidated at edge cases like this
            throw new ShieldExceptions("Logout Failed",  "Authentication process failed due to internal server error",500);
        }
        return authResponse;
    }

    @Override
    public RegistrationResponseModal register(RegistrationModal registerModal) throws ShieldExceptions {
        return shieldCore.performRegistration(shieldMapper.convertRegistrationModaltoDTO(registerModal));
    }

    @Override
    public AuthVerifierModal isAuthenticated(){
        return shieldCore.verifyCurrentSession();
    }

    @Override
    public RegistrationResponseModal updateRegistration(String userId, RegisterUpdateModal updateModal) throws ShieldExceptions {
        return shieldCore.updateRegistrationDetails(shieldMapper.convertUpdateRegistrationModaltoDTO(userId, updateModal));
    }

}