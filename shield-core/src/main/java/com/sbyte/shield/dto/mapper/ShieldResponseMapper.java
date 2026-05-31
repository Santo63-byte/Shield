package com.sbyte.shield.dto.mapper;

import com.sbyte.shield.modals.ShieldResponse;
import org.springframework.security.core.Authentication;

// define mappers to convert various objects to ShieldResponse
public interface ShieldResponseMapper {

    default ShieldResponse convertAuthenticationToShieldResponse(Authentication authentication) {
        return new ShieldResponse();
    }
}
