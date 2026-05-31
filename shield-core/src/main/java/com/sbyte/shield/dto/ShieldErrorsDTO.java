package com.sbyte.shield.dto;


import com.sbyte.shield.core.base.abst.Error;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ShieldErrorsDTO implements Error, Serializable {

    private  static final long serialVersionUID = 1L;

    private String code = "SHD";
    private String message = "No Errors";
    private String details ;
    private String moreInfo;
    private String traceId;
    private String timestamp;
    private int status = 200; // Default to 200 OK if not set
    @Override
    public int getStatus() {
        return status;
    }

}
