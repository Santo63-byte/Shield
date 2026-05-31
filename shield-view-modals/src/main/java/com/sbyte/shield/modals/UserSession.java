package com.sbyte.shield.modals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {
    @JsonProperty("session_id")
    private String sessionId;
    @JsonProperty("user_name")
    private String username;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("login_timestamp")
    private Long loginTimestamp;
    @JsonProperty("expiry_timestamp")
    private Long expiryTimestamp;

}
