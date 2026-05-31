package com.sbyte.shield.modals;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginModal {

    @NotBlank
    @JsonProperty("credentials")
    private CredentialsModal credentials;

    @JsonProperty("device")
    private String device;

    @JsonProperty("company_info")
    private CompanyModal companyInfo;

    private transient HttpServletResponse httpContextResponse;

}
