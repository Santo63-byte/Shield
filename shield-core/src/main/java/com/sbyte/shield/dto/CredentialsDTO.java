package com.sbyte.shield.dto;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class CredentialsDTO extends BasicUserCredentialDTO implements Serializable {

    private  static final long serialVersionUID = 1L;
    private String credentialId;
    private String token;
    private String action;
    private String captcha;
    private String refreshToken;
    private Long expiresIn;
    private String pwd2fa;
    private CompanyDTO company;
    private String deviceId;
    private String ssnid;
    private boolean forcedAction;  /// Indicates if user is required to perform forced actions like password change, 2FA setup, etc.
    private BasicUserCredentialDTO UserPreviousContextData;
    private transient HttpServletResponse httpcontextResponse;


}
