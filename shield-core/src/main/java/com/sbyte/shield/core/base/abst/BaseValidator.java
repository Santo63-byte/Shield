package com.sbyte.shield.core.base.abst;
/**
 * BaseValidator interface for validating objects with optional source context.
 *
 * @param <O1> the type of the object to be validated
 * @param <O2> the type of the errors object to collect validation errors
 */

public interface BaseValidator<O1,O2> {

    void validate(O1 target, O2 errors, String source);

    void validate(O1 target, O2 errors);

    void doValidate(O1 target, O2 errors);

    void validationException(O2 errors);


}
