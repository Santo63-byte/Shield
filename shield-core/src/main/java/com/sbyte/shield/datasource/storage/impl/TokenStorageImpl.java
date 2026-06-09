package com.sbyte.shield.datasource.storage.impl;

import com.sbyte.shield.dto.BklTokenDTO;
import com.sbyte.shield.dto.RFTokenDTO;
import com.sbyte.shield.datasource.entity.token.RFToken;
import com.sbyte.shield.datasource.jpa.BklTokenRepository;
import com.sbyte.shield.datasource.jpa.TokenRepository;
import com.sbyte.shield.datasource.entity.token.BklToken;
import com.sbyte.shield.datasource.mapper.ShieldDTOEntityMapper;
import com.sbyte.shield.datasource.storage.TokenStorage;
import com.sbyte.shield.dto.UserSessionDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("tokenStorage")
public class TokenStorageImpl implements TokenStorage {

    private final TokenRepository tokenRepository;
    private final BklTokenRepository bklTokenRepository;
    private final ShieldDTOEntityMapper dtoEntityMapper;

    public TokenStorageImpl(TokenRepository tokenRepository,
                            BklTokenRepository bklTokenRepository,
                            ShieldDTOEntityMapper dtoEntityMapper) {
        this.tokenRepository = tokenRepository;
        this.bklTokenRepository = bklTokenRepository;
        this.dtoEntityMapper = dtoEntityMapper;
    }

    @Override
    @Transactional
    public void persistToken(RFTokenDTO refreshToken) {
        refreshToken.setCreatedAt(java.time.Instant.now().toEpochMilli());
        RFToken rfEntity = dtoEntityMapper.mapRFTokenDTOToEntity(refreshToken);
        rfEntity.setTimestamp(LocalDateTime.now());
        tokenRepository.save(rfEntity);
    }

    @Override
    @Transactional
    public void deleteRefreshToken(UserSessionDTO userSession) {
        String loggedInUserId = userSession.getLoggedInUserId();
        String sessionId = userSession.getSessionId();
        Optional<RFToken> rfToken = tokenRepository.findByLoggedInUserIdAndSessionId(loggedInUserId, sessionId);

        rfToken.ifPresent(tokenRepository::delete);
    }

    @Override
    @Transactional
    public void moveTokentoBlackList(UserSessionDTO userSession) {
            // Create a new BklToken entity from UserSessionDTO
            BklToken bklToken = new BklToken();
            bklToken.setToken(userSession.getAccessToken());
            bklToken.setUserId(userSession.getLoggedInUserId());
            long currentTime = System.currentTimeMillis();
            bklToken.setCreatedAt(currentTime);
            bklToken.setExpiresAt(currentTime + (5 * 60 * 1000)); // 5 minutes from now
            bklToken.setSessionId(userSession.getSessionId());
            bklToken.setDeviceId(userSession.getDeviceId());
            bklToken.setTimestamp(LocalDateTime.now());
            // Save to BklToken (blacklist)
            bklTokenRepository.save(bklToken);
    }

    @Override
    @Transactional
    public List<BklTokenDTO> getBlackListedTokensByUserId(String userId) {
        List<BklToken> bklTokens = bklTokenRepository.findAllByUserId(userId);
        if(bklTokens.isEmpty()) {
            return List.of();
        }
        return bklTokens.stream()
                .map(dtoEntityMapper::mapBklTokenEntitiesToDTOs)
                .toList();
    }
}


