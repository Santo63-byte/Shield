package com.sbyte.shield.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class CompanyDTO implements Serializable {

    private  static final long serialVersionUID = 1L;

    private String companyName;

    private String companyCode;
}
