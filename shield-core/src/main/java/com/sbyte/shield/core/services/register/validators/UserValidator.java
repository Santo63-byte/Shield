package com.sbyte.shield.core.services.register.validators;

import com.sbyte.shield.core.cache.RegisterCacheStore;
import com.sbyte.shield.dto.ShieldErrorsDTO;
import com.sbyte.shield.core.base.impl.ShieldBaseValidator;
import com.sbyte.shield.dto.RegistrationDTO;
import com.sbyte.shield.datasource.entity.ledger.UserCredInfo;
import com.sbyte.shield.datasource.mybatis.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sbyte.shield.constants.ShieldConstants.*;

@Component("userValidator")
public class UserValidator extends ShieldBaseValidator<RegistrationDTO, ShieldErrorsDTO> {

    private static final String EMAIL_TAKEN_CODE = "EMAIL_TAKEN";
    private static final String EMAIL_TAKEN_MESSAGE = "The email is already registered in the system.";
    private static final String USERNAME_TAKEN_CODE = "USERNAME_TAKEN";
    private static final String USERNAME_TAKEN_MESSAGE = "The username is already taken.";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterCacheStore registerCacheStore;

    @Override
    public void doValidate(RegistrationDTO registrationDTO, ShieldErrorsDTO error) {
        if (isEmailTaken(registrationDTO)) {
            error.setCode(EMAIL_TAKEN_CODE);
            error.setMessage(EMAIL_TAKEN_MESSAGE);
            error.setStatus(VALIDATION_ERROR_CODE);
        }
        if (isUsernameTaken(registrationDTO)) {
            error.setCode(USERNAME_TAKEN_CODE);
            error.setMessage(USERNAME_TAKEN_MESSAGE);
            error.setStatus(VALIDATION_ERROR_CODE);
        }
    }

    private boolean isEmailTaken(RegistrationDTO registrationDTO) {
        return isValueTaken(
            SHIELD_REGISTERED_EMAILS_CACHE,
            UserCredInfo::getUserEmail,
            registrationDTO.getEmail()
        );
    }

    private boolean isUsernameTaken(RegistrationDTO registrationDTO) {
        return isValueTaken(
            SHIELD_REGISTERED_USERNAMES_CACHE,
            UserCredInfo::getUserName,
            registrationDTO.getUserName()
        );
    }

    private boolean isValueTaken(String cacheKey, Function<UserCredInfo, String> valueExtractor, String valueToCheck) {
        if (registerCacheStore.isCacheEmpty(cacheKey)) {
            List<UserCredInfo> usersList;
            try {
                usersList = userRepository.findAllActiveUsers();
            }
            catch (Exception e) {
                throw new RuntimeException("SHIELD LOOKUP ERROR", e);
            }
            Set<String> valuesSet = usersList.stream()
                    .map(valueExtractor)
                    .collect(Collectors.toSet());
            return valuesSet.contains(valueToCheck);
        }
        return registerCacheStore.isPresentInCache(cacheKey, valueToCheck);
    }
}
