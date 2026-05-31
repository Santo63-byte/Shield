package com.sbyte.shield.modals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUpdateModal {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

}
