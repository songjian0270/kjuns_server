package com.kjuns.annotation; 

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @comments 输入长度的范围（最大、最小值）
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LenRange {
	int maxLen();
	int minLen();
	String msg();
}
 