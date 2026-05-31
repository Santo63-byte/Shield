package com.sbyte.shield.constants;

import java.util.Map;

public class ShieldConstants {
    public static final int REGISTRATION_VALIDATION_PASS_CODE = 1;
    public static final int REGISTRATION_VALIDATION_FAIL_CODE = 0;
    public static final String REGISTRATION_VALIDATION_PASS_MESSAGE = "Registration completed successfully.";
    public static final String REGISTRATION_VALIDATION_FAIL_MESSAGE = "Registration failed with error code {}. Please check the provided details.";
    public static final String SHIELD_REGISTERED_USERS = "SHIELD_ACTIVE_USERS";
    public static final String SHIELD_REGISTERED_EMAILS_CACHE = "SHIELD_EMAILS_LIST";
    public static final String SHIELD_REGISTERED_USERNAMES_CACHE = "SHIELD_USERNAMES_LIST";
    public static final Map<String, String> SHIELD_USER_ROLES = Map.of(
            "I", "INDIVIDUAL",
            "A", "ADMIN",
            "M", "MODERATOR",
            "G", "GUEST"
    );
}