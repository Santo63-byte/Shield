package com.sbyte.shield.configurations.policy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "shield.policy")
@Getter
public class ShieldPolicy {

    private final Password password = new Password();
    private final DatePolicy date = new DatePolicy();

    @Setter
    @Getter
    public static class Password {
        private int minLength = 5;
        private int maxLength = 18;
    }
    @Setter
    @Getter
    public static class DatePolicy {
        private boolean allowFutureDates = true;
        private boolean allowPastDates = true;
    }

}
