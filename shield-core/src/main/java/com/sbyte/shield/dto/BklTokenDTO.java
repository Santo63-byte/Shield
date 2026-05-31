package com.sbyte.shield.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BklTokenDTO implements Serializable {

    private  static final long serialVersionUID = 1L;

    private String tokenId;

    private String accessToken;

    private String userId;

    private Long createdAt;

    private Long expiresAt;

    private String sessionId;

    private String deviceId;
}
