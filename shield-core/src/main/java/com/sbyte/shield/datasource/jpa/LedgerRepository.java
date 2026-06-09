package com.sbyte.shield.datasource.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sbyte.shield.datasource.entity.ledger.LedgerMaster;

public interface LedgerRepository extends JpaRepository<LedgerMaster, Long> {

    Optional<LedgerMaster> findByLedgerId(Long ledgerId);

    @Query("SELECT lm FROM LedgerMaster lm JOIN lm.userInfo ui WHERE ui.userIdRef = :userId")
    Optional<LedgerMaster> findByUserIdRef(@Param("userId") String userId);
}
