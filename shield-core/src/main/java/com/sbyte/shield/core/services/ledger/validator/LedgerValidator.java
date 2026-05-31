package com.sbyte.shield.core.services.ledger.validator;

import com.sbyte.shield.core.base.impl.ShieldBaseValidator;
import com.sbyte.shield.dto.ShieldErrorsDTO;
import com.sbyte.shield.dto.LedgerDTO;
import org.springframework.stereotype.Component;

@Component("ledgerValidator")
public class LedgerValidator extends ShieldBaseValidator<LedgerDTO, ShieldErrorsDTO> {
    // for ledger there are mainly 3 fields required to be validated
    // unique userid in the system
    // check if it already exists while creating new ledger entry
    //
    @Override
    public void doValidate(LedgerDTO ledgerDTO, ShieldErrorsDTO errors) {
        // Implement validation logic for LedgerDTO here
    }
}
