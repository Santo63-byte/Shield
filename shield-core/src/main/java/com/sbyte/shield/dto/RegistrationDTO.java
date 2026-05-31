package com.sbyte.shield.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
public class RegistrationDTO implements Serializable {

        private  static final long serialVersionUID = 1L;

        private boolean company;

        private boolean individual;

        private String companyName;
        private String companyCode;

        private String description;

        private String userId;

        private String userName;

        private String role;

        private OffsetDateTime createdAt;

        private OffsetDateTime updatedAt;

        @Email
        private String email;

        private String password;

        private String oldPassword;

        private String credentialId;

        private String adminName;

        private String serviceName;

        private String action;

        private String status;

        private String userDevice;

        // Added from UserInfo
        private String userFirstName;

        private String userLastName;

        private String userPhone;

        private String userAddress;

        private String userDOB;

        private String userIp;

        private String userAgent;

        private OffsetDateTime userActionTime;


}
