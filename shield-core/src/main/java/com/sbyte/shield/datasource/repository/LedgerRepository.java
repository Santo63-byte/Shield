package com.sbyte.shield.datasource.repository;

import com.sbyte.shield.datasource.entity.ledger.LedgerMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LedgerRepository extends JpaRepository<LedgerMaster, Long> {

    // Get ledgerId from UserInfo by userId
    @Query("SELECT ui.ledgerMaster.ledgerId FROM UserInfo ui WHERE ui.userIdRef = :userId")
    Optional<Long> findLedgerIdByUserId(@Param("userId") String userId);

    Optional<LedgerMaster> findByLedgerId(Long ledgerId);

    @Query("SELECT lm FROM LedgerMaster lm JOIN lm.userInfo ui WHERE ui.userIdRef = :userId")
    Optional<LedgerMaster> findByUserIdRef(@Param("userId") String userId);
}
