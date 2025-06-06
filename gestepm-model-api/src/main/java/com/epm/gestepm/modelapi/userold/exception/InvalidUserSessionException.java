package com.epm.gestepm.modelapi.userold.exception;

public class InvalidUserSessionException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidUserSessionException() {
		super("Unable to retrieve a valid user from session.");
	}	
}
