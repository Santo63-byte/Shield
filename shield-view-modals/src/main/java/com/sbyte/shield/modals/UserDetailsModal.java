package com.sbyte.shield.modals;
import com.fasterxml.jackson.annotation.JsonProperty;
import  lombok.Getter;
import  lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class UserDetailsModal {
    // not mandatory
    @JsonProperty("user_id")
    private String userId;

    // not mandatory
    @JsonProperty("user_name")
    private String userName;

    // mandatory
    @JsonProperty("role")
    private String role;

    // not mandatory (better for audit prpse)
    @JsonProperty("requested_timestamp")
    private OffsetDateTime createdAt;

    // mandatory
    @JsonProperty("credentials")
    private CredentialsModal credentials;

    //mandatory
    @JsonProperty("action")
    private String action;
}
