package com.sbyte.shield.core.services.ledger;

import com.sbyte.shield.dto.RegistrationDTO;
import com.sbyte.shield.dto.ShieldErrorsDTO;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import com.sbyte.shield.core.services.auditor.Auditor;
import com.sbyte.shield.core.services.ledger.validator.LedgerValidator;
import com.sbyte.shield.dto.LedgerDTO;
import com.sbyte.shield.dto.LedgerResponseDTO;
import com.sbyte.shield.datasource.storage.LedgerStorage;
import com.sbyte.shield.utils.SnowFlakesIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This component handles ledger activities like saving , updating, deleting, auditing info of user transactions
 * For every user registration a ledger entry is created as source of truth for user information
 * Every entry in ledger is audited for traceability.
 * Associated with a unique ledger id for every account created
 * @author Sans
 */

@Slf4j
@Component("ledger")
public class Ledger  {

    @Autowired
    private final LedgerValidator ledgerValidator;

    @Autowired
    private LedgerStorage ledgerStorage;

    @Autowired
    private Auditor auditor;

    @Autowired
    private SnowFlakesIDGenerator idGenerator;

    public Ledger(LedgerValidator ledgerValidator, LedgerStorage ledgerStorage, Auditor auditor, SnowFlakesIDGenerator idGenerator) {
        this.ledgerValidator = ledgerValidator;
        this.ledgerStorage = ledgerStorage;
        this.auditor = auditor;
        this.idGenerator = idGenerator;
    }

    public LedgerResponseDTO save(LedgerDTO ledgerDTO) {
        LedgerResponseDTO response = new LedgerResponseDTO();
        ledgerDTO.setLedgerId(idGenerator.nextId());
        ledgerDTO.getRegistrationDTO().setStatus("ACTIVE");
        try {
            ledgerStorage.persist(ledgerDTO);
        }
        catch (Exception e) {
            ShieldErrorsDTO error = new ShieldErrorsDTO();
            error.setCode("SHIELD_LEDGER_SAVE_ERROR");
            error.setMessage("Some problems encountered. More details :: Error saving ledger information.");
            throw new ShieldExceptions(error);
        }
        response.setLedgerId(ledgerDTO.getLedgerId());
        response.setDescription("SAVE_SUCCESS");
        response.setUserId(ledgerDTO.getRegistrationDTO().getUserId());
        response.setTransactionType(ledgerDTO.getAction());
        response.setUserName(ledgerDTO.getRegistrationDTO().getUserName());
        response.setUserId(ledgerDTO.getRegistrationDTO().getUserId());
        response.setUserEmail(ledgerDTO.getRegistrationDTO().getEmail());
        return response;
    }
    public void prepareLedgerForUpdate(LedgerDTO ledgerDTO, RegistrationDTO registerDTO) {
        String username = registerDTO.getUserName();
        String email = registerDTO.getEmail();
        String phone = registerDTO.getUserPhone();
        if (username != null && !username.isEmpty()) {
            ledgerDTO.getRegistrationDTO().setUserName(username);
            log.debug("Prepared ledger for username update: {}", username);
        }
        if (email != null && !email.isEmpty()) {
            ledgerDTO.getRegistrationDTO().setEmail(email);
            log.debug("Prepared ledger for email update: {}", email);
        }
        if (phone != null && !phone.isEmpty()) {
            ledgerDTO.getRegistrationDTO().setUserPhone(phone);
            log.debug("Prepared ledger for phone update: {}", phone);
        }

    }
    public LedgerResponseDTO update(RegistrationDTO updatedInfo, boolean credentialChange) {
        LedgerResponseDTO response = new LedgerResponseDTO();
        LedgerDTO ledger = fetchLedgerInfo(updatedInfo.getUserId()); // fetching existing ledger info
        if(!credentialChange) {// preparing ledger with updated info
            prepareLedgerForUpdate(ledger, updatedInfo);
        }
        try {
            ledgerStorage.persist(ledger);
        }
        catch (Exception e) {
            ShieldErrorsDTO error = new ShieldErrorsDTO();
            error.setCode("SHIELD_LEDGER_UPDATE_ERROR");
            error.setMessage("Some problems encountered. More details :: Error updating ledger information.");
            throw new ShieldExceptions(error);
        }
        response.setLedgerId(ledger.getLedgerId());
        response.setDescription("UPDATE_SUCCESS");
        response.setUserId(ledger.getRegistrationDTO().getUserId());
        response.setTransactionType(ledger.getAction());
        return response;
    }
    public LedgerResponseDTO delete(LedgerDTO ledgerDTO) {
        LedgerResponseDTO response = new LedgerResponseDTO();
        ledgerStorage.evict(ledgerDTO);
        return response;
    }
    public LedgerDTO fetchLedgerInfo(String userId) {
        return ledgerStorage.fetchLedgerInfoByUserId(userId);
    }

}