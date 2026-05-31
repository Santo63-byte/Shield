package com.sbyte.shield.core.services.register;

import com.sbyte.shield.core.cache.RegisterCacheStore;
import com.sbyte.shield.dto.LedgerDTO;
import com.sbyte.shield.dto.LedgerResponseDTO;
import com.sbyte.shield.dto.RegistrationDTO;
import com.sbyte.shield.modals.RegistrationResponseModal;
import com.sbyte.shield.modals.ShieldMessages;
import com.sbyte.shield.modals.UserProfileModal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sbyte.shield.constants.ShieldConstants.SHIELD_REGISTERED_EMAILS_CACHE;
import static com.sbyte.shield.constants.ShieldConstants.SHIELD_REGISTERED_USERNAMES_CACHE;

@Slf4j
@Component("registerUtil")
public class RegisterUtil {
    @Autowired
    private RegisterCacheStore registerCacheStore;

    public void updateInMemoryStore(RegistrationDTO registrationDTO) {
        log.debug("Starting in-memory cache update for userId: {}", registrationDTO.getUserId());
        if(registrationDTO.getEmail() != null && !registrationDTO.getEmail().isEmpty()) {
            log.debug("Adding email to cache store: {}", registrationDTO.getEmail());
            registerCacheStore.put(SHIELD_REGISTERED_EMAILS_CACHE, registrationDTO.getEmail());
            log.debug("Email successfully added to cache");
        }
        if(registrationDTO.getUserName() != null && !registrationDTO.getUserName().isEmpty()) {
            log.debug("Adding username to cache store for userId: {}", registrationDTO.getUserId());
            registerCacheStore.put(SHIELD_REGISTERED_USERNAMES_CACHE, registrationDTO.getUserName());
            log.debug("Username successfully added to cache");
        }
        log.debug("In-memory cache update completed for userId: {}", registrationDTO.getUserId());
    }
    public void postprocessResponse(RegistrationResponseModal response, LedgerResponseDTO ledgerResponseDTO) {
        response.setStatus("SUCCESS");
        ShieldMessages message = new ShieldMessages();
        message.setStatus(200);
        message.setCode("REGISTRATION_SUCCESS");
        message.setMessage("User registered successfully");
        UserProfileModal userProfileModal = new UserProfileModal();
        userProfileModal.setUserId(ledgerResponseDTO.getUserId());
        userProfileModal.setUserName(ledgerResponseDTO.getUserName());
        userProfileModal.setEmail(ledgerResponseDTO.getUserEmail());
        response.setMessage(message);
        response.setUserProfile(userProfileModal);
        log.debug("Registration response successfully built with user ID: {}", ledgerResponseDTO.getUserId());
    }
}
