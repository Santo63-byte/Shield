package com.sbyte.shield.core.base.impl;

import com.sbyte.shield.configurations.encoders.ShieldEncoders;
import com.sbyte.shield.configurations.policy.ShieldPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("credentialSupport")
public class CredentialSupport {
    @Autowired
    public ShieldEncoders shieldEncoders;
    @Autowired
    public ShieldPolicy shieldPolicy;

    public CredentialSupport(ShieldEncoders shieldEncoders, ShieldPolicy shieldPolicy) {
        this.shieldEncoders = shieldEncoders;
        this.shieldPolicy = shieldPolicy;
    }

}
