package com.sbyte.shield.core.base.impl;
//////////////////////////////////////////////////////
import com.sbyte.shield.core.base.abst.FunctionsBase;
import com.sbyte.shield.core.exceptions.ShieldExceptions;
import lombok.Getter;
import lombok.Setter;

/////////////////////////////////////////////////////
/**
 * Base class for core functions
 * @param <O1> input object type
 * @param <O2> output object type
 * @author Santo
 */
/////////////////////////////////////////////////////
@Getter
@Setter
public abstract class CoreServiceBase<O1,O2> implements FunctionsBase<O1,O2> {

    protected String functionName;

    public String idempotentKey;

    public String traceId;

    public String invocationSource;

    public void preExecute(){
        System.out.println("No pre-execution steps done");
    };
    public O2 postExecute(){
        return null;
    };

    public abstract O2 execute (O1 o1) throws ShieldExceptions;

    public void validate(O1 o1) throws ShieldExceptions{
        // Default implementation does nothing
        System.out.println("No validation done");
    };

    public void perform(O1 o1) throws ShieldExceptions{};

    public void enrichDTO(O1 o1) throws ShieldExceptions{
        // Default implementation does nothing
        System.out.println("No enrichments done");
    };

    public O2 fire(O1 o1) throws ShieldExceptions{
        this.preExecute();
        this.validate(o1);
        this.enrichDTO(o1);
        try {
            this.perform(o1); // for alteration operations or can be used as standalone fn call
            return this.execute(o1); // main exec
        }
        catch (ShieldExceptions e) {
            throw new ShieldExceptions(e.getMessage());
        }
    }
}