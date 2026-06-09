package com.sbyte.shield.datasource.jpa;

import com.sbyte.shield.datasource.entity.token.RFToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RFToken, Long> {

    Optional<RFToken> findByLoggedInUserIdAndSessionId(String loggedInUserId, String sessionId);

    void deleteByLoggedInUserId(String loggedInUserId);
}
