package com.sbyte.shield.core.services.register;

import com.sbyte.shield.core.base.impl.CredentialSupport;
import com.sbyte.shield.core.cache.RegisterCacheStore;
import com.sbyte.shield.dto.ShieldErrorsDTO;
import com.sbyte.shield.core.base.impl.CoreServiceBase;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.core.services.ledger.Ledger;
import com.sbyte.shield.core.services.register.validators.CredentialsValidator;
import com.sbyte.shield.core.services.register.validators.UserValidator;
import com.sbyte.shield.dto.LedgerDTO;
import com.sbyte.shield.dto.LedgerResponseDTO;
import com.sbyte.shield.dto.RegistrationDTO;
import com.sbyte.shield.modals.RegistrationResponseModal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.huxhorn.sulky.ulid.ULID;
import static com.sbyte.shield.constants.ShieldConstants.*;


@Log4j2
@Component("userRegister")
public class Register extends CoreServiceBase<RegistrationDTO, RegistrationResponseModal>{

    @Autowired
    private CredentialsValidator credentialsValidator;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private Ledger ledgerservice;

    @Autowired
    private RegisterCacheStore registerCacheStore;

    @Autowired
    private CredentialSupport credentialSupport;

    @Autowired
    RegisterUtil registerUtil;

    @Override
    public void validate(RegistrationDTO registrationDTO) {
        log.info("Starting validation for registration DTO ");
        ShieldErrorsDTO errors = new ShieldErrorsDTO();
        userValidator.validate(registrationDTO, errors);
        credentialsValidator.validate(registrationDTO, errors);
    }
    @Override
    public RegistrationResponseModal execute(RegistrationDTO registrationDTO) throws ShieldExceptions {
        log.info("Starting user registration process for userId: {}", registrationDTO.getUserId());
        RegistrationResponseModal response = new RegistrationResponseModal();
        LedgerDTO ledger = new LedgerDTO();
        ledger.setRegistrationDTO(registrationDTO);
        LedgerResponseDTO ledgerResponseDTO;
        try {
            log.debug("Initiating ledger transaction for userId: {}", registrationDTO.getUserId());
            ledgerResponseDTO= ledgerservice.save(ledger);
            log.info("Ledger transaction completed successfully with user ID: {}", ledgerResponseDTO.getUserId());
        }
        catch (Exception e){
            log.error("Error occurred during ledger transaction for userId {}: {}", registrationDTO.getUserId(), e.getMessage(), e);
            ShieldErrorsDTO error = new ShieldErrorsDTO();
            error.setMessage("Error during ledger transaction via register: " + e.getMessage());
            throw new ShieldExceptions(error);
        }
        // updating in-memory store
        log.debug("Updating in-memory cache store for userId: {}", registrationDTO.getUserId());
        registerUtil.updateInMemoryStore(registrationDTO);
        // sending confirmation via email or sms   this should be async
        // sending response
        registerUtil.postprocessResponse(response, ledgerResponseDTO);
        log.info("User registration completed successfully for user ID: {}", ledgerResponseDTO.getUserId());
        return response;
    }
    private String generateUserId() {
        log.debug("Generating new ULID for user");
        ULID ulid = new ULID();
        String generatedId = ulid.nextULID();
        log.debug("New ULID generated successfully: {}", generatedId);
        return generatedId;
    }

    @Override
    public void enrichDTO(RegistrationDTO registrationDTO) {
        log.info("Starting DTO enrichment for user");
        if(registrationDTO.getCreatedAt() == null ){
            log.debug("Setting createdAt timestamp for user");
            registrationDTO.setCreatedAt(java.time.OffsetDateTime.now());
        }
        registrationDTO.setUpdatedAt(java.time.OffsetDateTime.now());
        log.debug("Updated timestamp set for user");
        if(registrationDTO.getRole() == null || registrationDTO.getRole().isEmpty()){
            log.debug("Setting default role for user");
            registrationDTO.setRole(SHIELD_USER_ROLES.get("I"));
        }
        if(registrationDTO.getUserId() == null || registrationDTO.getUserId().isEmpty()){
            log.debug("Generating user ID for user");
            registrationDTO.setUserId(generateUserId());
        }
        hash(registrationDTO);
        log.info("DTO enrichment completed successfully for user with ID: {}", registrationDTO.getUserId());
    }

    private void hash(RegistrationDTO registrationDTO) {
        log.debug("Hashing password for user: {}", registrationDTO.getUserName());
        registrationDTO.setPassword(credentialSupport.shieldEncoders.passwordEncoder().encode(registrationDTO.getPassword()));
        log.debug("Password successfully hashed for user");
    }
}
