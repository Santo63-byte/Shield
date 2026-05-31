package com.sbyte.shield.core.services.register.validators;

import com.sbyte.shield.core.base.impl.ShieldBaseValidator;
import com.sbyte.shield.dto.RegistrationDTO;
import com.sbyte.shield.dto.ShieldErrorsDTO;
import org.springframework.stereotype.Component;

import static com.sbyte.shield.constants.ShieldMessageShortCodes.SHIELD_PSWD_LEN_NOT_MET;
import static com.sbyte.shield.constants.ShieldMessageShortCodes.SHIELD_PSWD_EMPTY;
import static com.sbyte.shield.constants.ShieldValidations.SHIELD_PASSWORD_LENGTH_MESSAGE;
import static com.sbyte.shield.constants.ShieldValidations.SHIELD_PASSWORD_NOT_EMPTY_MESSAGE;

@Component("credentialsValidator")
public class CredentialsValidator extends ShieldBaseValidator<RegistrationDTO, ShieldErrorsDTO> {

    @Override
    public void doValidate(RegistrationDTO registrationDTO, ShieldErrorsDTO error) {
        String password = registrationDTO.getPassword();
        String registersource = "RGS";
        if(source.equals(registersource)) {
            if (isNullOrBlank(password)) {
                error.setCode(SHIELD_PSWD_EMPTY);
                error.setMessage(SHIELD_PASSWORD_NOT_EMPTY_MESSAGE);
                error.setStatus(VALIDATION_ERROR_CODE);
                return;
            }
            if (!acceptedPasswordLength(password, error)) {
                error.setCode(SHIELD_PSWD_LEN_NOT_MET);
                error.setMessage(SHIELD_PASSWORD_LENGTH_MESSAGE);
                error.setStatus(VALIDATION_ERROR_CODE);
            }
        }
    }
    public boolean acceptedPasswordLength(String password, ShieldErrorsDTO errors) {
        int minLength = shieldPolicy.getPassword().getMinLength();
        int maxLength = shieldPolicy.getPassword().getMaxLength();
        return hasMinLength(password, minLength) && hasMaxLength(password, maxLength);
    }
}
