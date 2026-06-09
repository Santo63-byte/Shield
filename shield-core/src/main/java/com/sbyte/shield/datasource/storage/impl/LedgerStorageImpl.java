package com.sbyte.shield.datasource.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbyte.shield.datasource.entity.ledger.LedgerMaster;
import com.sbyte.shield.datasource.jpa.LedgerRepository;
import com.sbyte.shield.datasource.mapper.ShieldDTOEntityMapper;
import com.sbyte.shield.datasource.storage.LedgerStorage;
import com.sbyte.shield.dto.LedgerDTO;


@Service("ledgerStorage")
@Transactional
public class LedgerStorageImpl implements LedgerStorage  {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private ShieldDTOEntityMapper shieldDTOEntityMapper;

    @Override
    public void persist(LedgerDTO ledgerDTO) {
        ledgerRepository.save(shieldDTOEntityMapper.mapLedgerDTOToEntity(ledgerDTO));
    }
    @Override
    public void evict(LedgerDTO ledgerDTO) {
        ledgerRepository.delete(shieldDTOEntityMapper.mapLedgerDTOToEntity(ledgerDTO));
    }

    @Override
    public LedgerDTO fetchLedgerInfoByUserId(String userId) {
        LedgerMaster ledgerMaster = ledgerRepository.findByUserIdRef(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        return shieldDTOEntityMapper.mapEntityToLedgerDTO(ledgerMaster);

    }
}
