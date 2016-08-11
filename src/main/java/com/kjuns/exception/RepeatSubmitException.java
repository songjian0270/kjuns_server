package com.kjuns.exception;

public class RepeatSubmitException extends Exception{
	
	private static final long serialVersionUID = -2334702025357451328L;

	public RepeatSubmitException() {
		super();
	}
	
	public RepeatSubmitException(String message) {
		super(message);
	}

}
