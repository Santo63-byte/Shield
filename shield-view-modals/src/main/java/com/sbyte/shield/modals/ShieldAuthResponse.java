package com.sbyte.shield.modals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShieldAuthResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty("bearer_token")
    private String bearerToken;

    @JsonProperty("indicators")
    private IndicatorsModal indicators;

    @JsonProperty("user_session")
    private UserSession userSession;

    private transient  CookieData refreshTokenCookie;

    @Getter
    @Setter
    public static class CookieData {
        private String name;
        private String value;
        private int maxAge;
        private boolean httpOnly;
        private boolean secure;
        private String path;
    }
}
