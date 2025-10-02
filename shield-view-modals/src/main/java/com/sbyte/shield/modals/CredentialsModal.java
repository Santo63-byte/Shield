package com.sbyte.shield.modals;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CredentialsModal {
    //mandatory field
    @JsonProperty("contact_email")
    private String email;

    //mandatory field
    @JsonProperty("password")
    private String password;


    //mandatory field if admin_name is not there for service account
    @JsonProperty("admin_name")
    private String adminName;

    //mandatory field
    @JsonProperty("service_name")
    private String serviceName;
}
