package com.sbyte.shield.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class LedgerResponseDTO  implements Serializable {

    private  static final long serialVersionUID = 1L;
    private Long ledgerId;
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String transactionType;
    private String description;
    private String timestamp;
    private int transactionCode;
}
