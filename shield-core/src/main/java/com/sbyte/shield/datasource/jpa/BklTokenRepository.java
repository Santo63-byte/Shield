package com.sbyte.shield.datasource.jpa;

import com.sbyte.shield.datasource.entity.token.BklToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BklTokenRepository extends JpaRepository<BklToken, String> {

    List<BklToken> findAllByUserId(String userId);
}

