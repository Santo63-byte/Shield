package com.sbyte.shield.modals;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyModal {

    @JsonProperty("name")
    private String companyName;

    @JsonProperty("code")
    private String companyCode;

    @JsonProperty("description")
    private String description;
}
