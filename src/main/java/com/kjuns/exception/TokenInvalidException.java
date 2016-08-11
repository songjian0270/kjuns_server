package com.kjuns.exception;

/**
 * <b>Function: </b>
 * 
 * @author James
 * @date 2015-8-19
 * @file TokenInvalidException.java
 * @package com.kjuns.exception
 * @project kjuns
 * @version 2.0
 */
public class TokenInvalidException extends Exception {

	private static final long serialVersionUID = -5043646291353854229L;
	
	public TokenInvalidException() {
		super();
	}
	
	public TokenInvalidException(String message) {
		super(message);
	}

}
