package com.sbyte.shield.datasource.storage;

import com.sbyte.shield.dto.LedgerDTO;

public interface LedgerStorage {

    void persist(LedgerDTO ledger);

    void evict(LedgerDTO ledger);

    LedgerDTO fetchLedgerInfoByUserId(String userId);



}
