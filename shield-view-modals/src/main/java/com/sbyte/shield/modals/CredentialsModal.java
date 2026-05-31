package com.sbyte.shield.modals;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CredentialsModal {
    //mandatory field depends on mode
    @JsonProperty("user_name")
    private String userName;

    //mandatory field depends on mode
    @JsonProperty("contact_email")
    private String email;

    //mandatory field
    @JsonProperty("password")
    private String password;

    //not mandatory field (optional and it is generated only on initial save)
    @JsonProperty("credential_id")
    private String credentialId;

    //nonmandatory field
    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("captcha_token")
    private String captchaToken;

    @JsonProperty("phone_number")
    private String phoneNumber;

}
