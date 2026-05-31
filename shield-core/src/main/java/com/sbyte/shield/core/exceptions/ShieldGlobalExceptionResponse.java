package com.sbyte.shield.core.exceptions;

import com.sbyte.shield.dto.ShieldErrorsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ShieldGlobalExceptionResponse {

    @ExceptionHandler(ShieldExceptions.class)
    public ResponseEntity<Map<String, Object>> handleShieldExceptions(ShieldExceptions ex) {
        ShieldErrorsDTO errorDetails = ex.getErrorDetails();

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Shield Exception occurred");
        errorResponse.put("code", errorDetails.getCode());
        errorResponse.put("description", errorDetails.getMessage());
        errorResponse.put("status", errorDetails.getStatus());

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        try {
            int statusCode = errorDetails.getStatus();
            httpStatus = HttpStatus.valueOf(statusCode);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
