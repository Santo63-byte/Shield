package com.sbyte.shield.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserFilterDTO implements Serializable {

    private  static final long serialVersionUID = 1L;
    private  String userName;
    private  String userEmail;

}
