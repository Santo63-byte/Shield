package com.sbyte.shield.modals;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LogoutModal {

    @JsonProperty("refresh_token")
    @NotBlank
    private String refreshToken;

    @JsonProperty("device")
    private String device;

    private transient HttpServletResponse httpContextResponse;

}
