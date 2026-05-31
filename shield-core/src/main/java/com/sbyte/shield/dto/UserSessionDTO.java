package com.sbyte.shield.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSessionDTO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String loggedInUserId;
    private String sessionId;
    private String deviceId;
    private String accessToken;
}
