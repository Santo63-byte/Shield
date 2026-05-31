package com.sbyte.shield.core.services.authenticator.validators;

import com.sbyte.shield.core.base.impl.ShieldBaseValidator;
import com.sbyte.shield.dto.CredentialsDTO;
import com.sbyte.shield.dto.ShieldErrorsDTO;
import org.springframework.stereotype.Component;

@Component("autherizationValidator")
public class AuthorizationValidator extends ShieldBaseValidator<CredentialsDTO, ShieldErrorsDTO> {

    @Override
    public void doValidate(CredentialsDTO dto, ShieldErrorsDTO errors) {
        if (dto.getUserName() == null && dto.getEmail() == null ) {
            errors.setMessage("Username/Email cannot be empty");
            errors.setStatus(400);
            return;
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            errors.setMessage("Password cannot be empty");
            errors.setStatus(400);
        }
    }

}
