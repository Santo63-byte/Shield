package com.sbyte.shield.modals;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyModal {

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("company_code")
    private String companyCode;

}
