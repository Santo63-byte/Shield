package com.sbyte.shield.core.base.impl;

import com.sbyte.shield.configurations.policy.ShieldPolicy;
import com.sbyte.shield.core.base.abst.BaseValidator;
import com.sbyte.shield.core.base.abst.Error;
import com.sbyte.shield.dto.ShieldErrorsDTO;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

/**
 * Base validator class providing common validation methods.
 * Extend this class to create specific validators with the generic types O1 and O2 objects to be validated. O2 is usually the errors object.
 * This class implements the BaseValidator interface and provides default implementations for the validate methods.
 * This contains the protected policy object to access policy configurations in child classes.
 * This class also handle auto exception throwing if the errors object has a getStatus method and status is not "200".
 * If you want custom error throwing, override the throwError() method in child class.
 * @param <O1> the data object type to be validated
 * @param <O2> the errors object type to collect validation errors
 * @Author: Santo
 */

@Component("shieldBaseValidator")
public abstract class ShieldBaseValidator<O1,O2 extends Error> implements BaseValidator<O1,O2 > {

    @Autowired
    protected ShieldPolicy shieldPolicy; // Access policy configurations in children using this obj.

    protected String source = "RGS"; // Default source

    protected int VALIDATION_ERROR_CODE = 400;   /// use this to set errorcode so validator automatically throws exception
    ///
    protected int VALIDATION_SUCCESS_CODE = 200;

    public boolean isSameValues(String val1, String val2) {
        return val1 != null && val1.equals(val2);
    }

    public boolean isNotNull(Object obj) {
        return obj != null;
    }

    public boolean isNull(Object obj) {
        return obj == null;
    }

    public boolean isDateInFuture(Date date) {
        return date != null && date.after(new Date());
    }

    public boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public boolean isDateInPast(Date date) {
        return date != null && date.before(new Date());
    }

    public boolean hasMinLength(String str, int minLength) {
        return str != null && str.length() >= minLength;
    }

    public boolean hasMaxLength(String str, int maxLength) {
        return str != null && str.length() <= maxLength;
    }
    @Override
    public abstract void doValidate(O1 o1, O2 o2) ;

    public void throwError(O2 o2) {}/// provision to override in child for custom error handling

    public void validationException(O2 o2) {
        /// For errors object having getStatus method, check status and throw exception if not success code

        try {
            Method getStatusMethod = o2.getClass().getMethod("getStatus");
            Object status = getStatusMethod.invoke(o2);

            if (!Objects.equals(status, VALIDATION_SUCCESS_CODE)) {
                if (o2 instanceof ShieldErrorsDTO) {
                    throw new ShieldExceptions((ShieldErrorsDTO) o2);
                }
                else{
                    throwError(o2);
                }
            }
        }
        catch (NoSuchMethodException e) {
            throw new ShieldExceptions("The errors object does not have a getStatus method", String.valueOf(e));
        } catch (Exception e) {
            throw new ShieldExceptions("Exception", String.valueOf(e),400);
        }
    }
    @Override
    public void validate(O1 o1, O2 o2, String source) {
        if(source != null && !source.isEmpty()) {
            this.source = source;
        }
        this.doValidate(o1, o2);
        this.validationException(o2);
    }

    @Override
    public void validate(O1 o1, O2 o2) {
        // Call doValidate with default source
        this.doValidate(o1, o2);
        this.validationException(o2);
    }
}
