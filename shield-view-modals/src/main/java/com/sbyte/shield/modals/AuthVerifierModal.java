package com.sbyte.shield.modals;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthVerifierModal {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("is_authenticated")
    private boolean isAuthenticated;

    @JsonProperty("role")
    private String role;

}
