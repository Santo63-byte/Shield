package com.sbyte.shield.datasource.storage;

import com.sbyte.shield.dto.BklTokenDTO;
import com.sbyte.shield.dto.RFTokenDTO;
import com.sbyte.shield.dto.UserSessionDTO;

import java.util.List;

public interface TokenStorage {

    void persistToken(RFTokenDTO refreshToken);

    void moveTokentoBlackList(UserSessionDTO userSession);

    void deleteRefreshToken(UserSessionDTO userSession);

    List<BklTokenDTO> getBlackListedTokensByUserId(String userId);

}
