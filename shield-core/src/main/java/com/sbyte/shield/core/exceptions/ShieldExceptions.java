package com.sbyte.shield.core.exceptions;

import com.sbyte.shield.dto.ShieldErrorsDTO;
import lombok.Getter;

@Getter

public class ShieldExceptions extends RuntimeException {

    public final ShieldErrorsDTO errorDetails;

    public ShieldExceptions(ShieldErrorsDTO errorDetails) {
        super(errorDetails.getMessage());
        this.errorDetails = errorDetails;
    }
    /**
     * Create exception with error details and cause
     */
    public ShieldExceptions(ShieldErrorsDTO errorDetails, Throwable cause) {
        super(errorDetails.getMessage(), cause);
        this.errorDetails = errorDetails;
    }
    public ShieldExceptions(String message) {
        super(message);
        this.errorDetails = new ShieldErrorsDTO();
        this.errorDetails.setMessage(message);
        this.errorDetails.setStatus(400);
        this.errorDetails.setCode("SHIELD_EXCEPTION");
    }
    /**
     * Create exception with code and message
     */
    public ShieldExceptions(String code, String message) {
        super(message);
        this.errorDetails = new ShieldErrorsDTO();
        this.errorDetails.setCode(code);
        this.errorDetails.setMessage(message);
        this.errorDetails.setStatus(400);
    }
    /**
     * Create exception with code, message, and status
     */
    public ShieldExceptions(String code, String message, int status) {
        super(message);
        this.errorDetails = new ShieldErrorsDTO();
        this.errorDetails.setCode(code);
        this.errorDetails.setMessage(message);
        this.errorDetails.setStatus(status);
    }
    /**
     * Create exception with full details
     */
    public ShieldExceptions(String code, String message, String details, String moreInfo, int status) {
        super(message);
        this.errorDetails = new ShieldErrorsDTO();
        this.errorDetails.setCode(code);
        this.errorDetails.setMessage(message);
        this.errorDetails.setDetails(details);
        this.errorDetails.setMoreInfo(moreInfo);
        this.errorDetails.setStatus(status);
    }
}
