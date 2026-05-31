package com.sbyte.shield.core.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a class for execution of a specific method on application startup.
 * The method specified in the annotation will be invoked when the application starts.
 * The class must be a spring-managed bean.
 * The annoted bean should have a no-argument method with the name provided in the annotation value.
 * @Author: Santo
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnApplicationStart {
    String value() default "init";
}
