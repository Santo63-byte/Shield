package com.sbyte.shield.core.base.abst;

import com.sbyte.shield.core.exceptions.ShieldExceptions;

public interface FunctionsBase<O1,O2> {

    O2 execute(O1 o1);

    void validate(O1 o1) throws ShieldExceptions;

    void enrichDTO(O1 o1) throws ShieldExceptions;

    O2 fire(O1 o1) throws ShieldExceptions;

    O2 postExecute();
}
