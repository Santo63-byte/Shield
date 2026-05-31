package com.sbyte.shield.core.services.register.validators;

import com.sbyte.shield.core.base.impl.ShieldBaseValidator;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.dto.RegistrationDTO;
import com.sbyte.shield.dto.ShieldErrorsDTO;
import org.springframework.stereotype.Component;

@Component("registerUpdateValidator")
public class RegisterUpdateValidator extends ShieldBaseValidator<RegistrationDTO, ShieldErrorsDTO> {
    @Override
    public void doValidate(RegistrationDTO registrationDTO, ShieldErrorsDTO errors) throws ShieldExceptions {
        if(registrationDTO.getUserId() == null || registrationDTO.getUserId().isEmpty()) {
            errors.setStatus(400);
            errors.setMessage("User ID is mandatory for update");
            errors.setCode("SHIELD_USER_UPDATE_ERROR");
            return;
        }
        if((registrationDTO.getUserName() == null || registrationDTO.getUserName().isEmpty()) &&
                (registrationDTO.getEmail() == null || registrationDTO.getEmail().isEmpty()) &&
                (registrationDTO.getUserPhone() == null || registrationDTO.getUserPhone().isEmpty())) {
            errors.setStatus(400);
            errors.setMessage("At least one of User Name, Email, or Phone Number must be provided for update");
            errors.setCode("SHIELD_USER_UPDATE_ERROR");
        }
    }
}
