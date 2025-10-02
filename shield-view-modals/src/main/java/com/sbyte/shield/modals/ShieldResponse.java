package com.sbyte.shield.modals;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import com.sbyte.shield.modals.ShieldMessages;
@Getter
@Setter
public class ShieldResponse {

    @JsonProperty("status")
    private String status;

    @JsonProperty ("indicators")
    private IndicatorsModal indicators;

    @JsonProperty("shieldmessages")
    private ShieldMessages message;

}