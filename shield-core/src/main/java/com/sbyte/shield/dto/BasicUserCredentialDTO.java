package com.sbyte.shield.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BasicUserCredentialDTO implements Serializable {

    private  static final long serialVersionUID = 1L;
    protected String userName;
    protected String password;
    protected String userId;
    protected  String email;
    protected String phoneNumber;
    protected  String serviceName;
    protected  String device;
}
