package com.sbyte.shield.core.base.abst;

import com.sbyte.shield.core.exceptions.ShieldExceptions;

public interface JobBase {

    void fire() throws ShieldExceptions;

    void perform() throws ShieldExceptions;

    void postExecute() throws ShieldExceptions;

    void executeOnProActive() throws ShieldExceptions;

    void executeOnApplicationReady() throws ShieldExceptions;
}

