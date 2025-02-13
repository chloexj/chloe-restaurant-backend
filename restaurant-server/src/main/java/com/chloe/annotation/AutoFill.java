package com.chloe.annotation;

import com.chloe.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//mark function which need auto fill

//Apply in method
@Target(ElementType.METHOD)
//when runtime fill
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //operation type: update or insert
    OperationType value();
}
