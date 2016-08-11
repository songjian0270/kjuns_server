package com.kjuns.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-19
 * @file VerifyToken.java
 * @package com.kjuns.annotation
 * @project kjuns
 * @version 2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface VerifyToken {

}
