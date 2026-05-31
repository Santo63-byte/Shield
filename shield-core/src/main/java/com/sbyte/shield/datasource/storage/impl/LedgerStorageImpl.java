package com.sbyte.shield.datasource.storage.impl;

import com.sbyte.shield.datasource.repository.LedgerRepository;
import com.sbyte.shield.dto.LedgerDTO;
import com.sbyte.shield.datasource.entity.ledger.LedgerMaster;
import com.sbyte.shield.datasource.mapper.ShieldDTOEntityMapper;
import com.sbyte.shield.datasource.storage.LedgerStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("ledgerStorage")
public class LedgerStorageImpl implements LedgerStorage  {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private ShieldDTOEntityMapper shieldDTOEntityMapper;

    @Override
    public void persist(LedgerDTO ledgerDTO) {
        ledgerRepository.save(shieldDTOEntityMapper.mapLedgerDTOToEntity(ledgerDTO));
    }
    public void evict(LedgerDTO ledgerDTO) {
        ledgerRepository.delete(shieldDTOEntityMapper.mapLedgerDTOToEntity(ledgerDTO));
    }

    public Long fetchLedgerIdByUserId(String userId) {
        return ledgerRepository.findLedgerIdByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    }

    public LedgerDTO fetchLedgerInfoByUserId(String userId) {
        LedgerMaster ledgerMaster = ledgerRepository.findByUserIdRef(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return shieldDTOEntityMapper.mapEntityToLedgerDTO(ledgerMaster);

    }
}
