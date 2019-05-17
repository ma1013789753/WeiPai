package com.jokerdata.common.annotation;
 
import java.lang.annotation.*;

/**
 * @Auth oldma
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
    boolean value() default false;
}