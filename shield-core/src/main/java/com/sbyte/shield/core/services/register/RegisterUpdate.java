package com.sbyte.shield.core.services.register;

import com.sbyte.shield.core.base.impl.CoreServiceBase;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.core.services.ledger.Ledger;
import com.sbyte.shield.core.services.register.validators.RegisterUpdateValidator;
import com.sbyte.shield.dto.LedgerResponseDTO;
import com.sbyte.shield.dto.RegistrationDTO;
import com.sbyte.shield.dto.ShieldErrorsDTO;
import com.sbyte.shield.modals.RegistrationResponseModal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("registerUpdate")
public class RegisterUpdate extends CoreServiceBase<RegistrationDTO, RegistrationResponseModal> {

    @Autowired
    Ledger ledgerservice;

    @Autowired
    RegisterUpdateValidator registerUpdateValidator;

    @Autowired
    private RegisterUtil registerUtil;

    @Override
    public void validate(RegistrationDTO registrationDTO) {
        ShieldErrorsDTO errors = new ShieldErrorsDTO();
        registerUpdateValidator.validate(registrationDTO, errors);
    }
    @Override
    public void enrichDTO(RegistrationDTO registrationDTO) {
        registrationDTO.setUpdatedAt(java.time.OffsetDateTime.now());
    }
    @Override
    public RegistrationResponseModal execute(RegistrationDTO registrationDTO) {
        LedgerResponseDTO ledgerResponseDTO;
        RegistrationResponseModal response = new RegistrationResponseModal();
        try {
            log.debug("Initiating ledger transaction for userId: {}", registrationDTO.getUserId());
            ledgerResponseDTO= ledgerservice.update(registrationDTO,false);
            log.info("Ledger transaction completed successfully with user ID: {}", ledgerResponseDTO.getUserId());
        }
        catch (Exception e){
            log.error("Error occurred during ledger transaction for userId {}: {}", registrationDTO.getUserId(), e.getMessage(), e);
            ShieldErrorsDTO error = new ShieldErrorsDTO();
            error.setMessage("Error during ledger transaction via register update: " + e.getMessage());
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
}
