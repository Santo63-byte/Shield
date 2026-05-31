package com.sbyte.shield.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RFTokenDTO implements Serializable {
    private  static final long serialVersionUID = 1L;
    private long tokenid;
    private String refreshToken;
    private String loggedInUserId;
    private Long createdAt;
    private Long expiresAt;
    private String sessionId;
    private String deviceId;

}
