package com.sbyte.shield.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class LedgerDTO implements Serializable {

    private  static final long serialVersionUID = 1L;

    private RegistrationDTO registrationDTO;

    private Long ledgerId;

    private String action;

}
