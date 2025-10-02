package com.sbyte.shield.modals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationModal {

    @JsonProperty("registration_type")
    private RegistrationTypeModal registrationType;

    @JsonProperty("company_info")
    private CompanyModal company;

    @JsonProperty("user_info")
    private UserDetailsModal user;

}
