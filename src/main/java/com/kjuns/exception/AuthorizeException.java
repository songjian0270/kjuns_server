package com.kjuns.exception;
/**
 * <b>Function: </b>     
 * @author James
 * @date 2015-8-23
 * @file AuthorizeException.java
 * @package com.kjuns.exception
 * @project kjuns
 * @version 2.0
 */
public class AuthorizeException extends Exception {
	
	private static final long serialVersionUID = -2334702025357451328L;

	public AuthorizeException() {
		super();
	}
	
	public AuthorizeException(String message) {
		super(message);
	}

}
