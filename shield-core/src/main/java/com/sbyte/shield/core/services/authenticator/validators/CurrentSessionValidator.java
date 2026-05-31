package com.sbyte.shield.core.services.authenticator.validators;

import com.sbyte.shield.core.base.impl.ShieldBaseValidator;
import com.sbyte.shield.core.services.authenticator.support.AuthSupport;
import com.sbyte.shield.dto.CredentialsDTO;
import com.sbyte.shield.dto.ShieldErrorsDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class CurrentSessionValidator extends ShieldBaseValidator<CredentialsDTO, ShieldErrorsDTO> {

    @Autowired
    private AuthSupport authSupport;

    @Override
    public void doValidate(CredentialsDTO credentialsDTO, ShieldErrorsDTO errorsDTO) {
        boolean authenticated = authSupport.getAuthenticationStatus().isAuthenticated();
        if (!authenticated) {
            errorsDTO.setMessage("Current session is not authenticated for provided user:"+ credentialsDTO.getUserName());
            errorsDTO.setCode("AUTH_401");
        }
    }
}
