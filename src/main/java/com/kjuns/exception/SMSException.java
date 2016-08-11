package com.kjuns.exception;
/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-23
 * @file SMSException.java
 * @package com.kjuns.exception
 * @project kjuns
 * @version 2.0
 */
public class SMSException extends Exception{
	
	private static final long serialVersionUID = 2728564446633472854L;

	public SMSException() {
		super();
	}
	
	public SMSException(String message) {
		super(message);
	}

}
