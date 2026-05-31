package com.sbyte.shield.modals;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticatorModal {

// This class represents the data structure for authentication requests,
// including username, password, and optional fields for two-factor authentication (2FA) and session management.
    @JsonProperty("username")
    private String username; // Mandatory username for authentication

    @JsonProperty("email")
    private String email; // Optional email for recovery or notifications

    @JsonProperty("password")
    private String password; // Mandatory password for authentication

    @JsonProperty("oneTimePassword")
    private String otp; // One-Time Password for 2FA

    @JsonProperty("token")
    private String token; // Optional token for session management

    @JsonProperty("sessionstatus")
    private String sessionStatus;

    @JsonProperty("accesstoken")
    private String accessToken;

    @JsonProperty("refreshtoken")
    private String refreshToken;

    @JsonProperty("companycode")
    private String companyCode; // mandatory company code for multi-tenancy or organizational context

    @JsonProperty("systemcode")
    private String systemCode; // Mandatory system code for identifying the system or application context

    @JsonProperty("systemname")
    private String systemName; // Optional system name for better identification


}
