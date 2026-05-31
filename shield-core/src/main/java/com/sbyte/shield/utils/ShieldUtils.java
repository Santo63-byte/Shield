package com.sbyte.shield.utils;


import org.springframework.stereotype.Component;

@Component("shieldUtils")
public class ShieldUtils {

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public String generateSessionId() {
        return java.util.UUID.randomUUID().toString();
    }

    public String hashValue(String token) {
        return Integer.toHexString(token.hashCode());
    }
    public String generateDeviceId(String deviceInfo) {
        if (deviceInfo == null || deviceInfo.isEmpty()) {
            return "unknown";
        }
        String deviceType = deviceInfo.toLowerCase().trim();
        String uniqueId = java.util.UUID.randomUUID().toString().substring(0, 8);
        return deviceType + "_" + uniqueId;
    }
}