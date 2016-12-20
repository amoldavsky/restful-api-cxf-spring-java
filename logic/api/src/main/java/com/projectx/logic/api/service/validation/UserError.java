package com.projectx.logic.api.service.validation;

/**
 * All User entity related errors with codes and messages
 * @author Assaf Moldavsky
 */

public enum UserError implements ApiError {

	INTERNAL_ERROR( -1000, "internal error" ),
	INVALID_USERNAME( -1100, "username is invalid! the username must be alphanumeric and can only contain '.','-'" );
	// TODO: Add more messages here

	private final int code;
	private final String message;
	
	UserError( int code, String message ) {
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
	
}
