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

    // mandatory
    @JsonProperty("user_name")
    private String userName;

    // mandatory
    @JsonProperty("contact_email")
    private String email;

    // not mandatory
    @JsonProperty("role")
    private String role;

    // not mandatory
    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    // not mandatory
    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;

    // mandatory
    @JsonProperty("credentials")
    private CredentialsModal credentials;

    //mandatory
    @JsonProperty("action")
    private String action;
}
