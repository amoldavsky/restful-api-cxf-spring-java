package com.projectx.logic.api.service.validation;

/**
 * All User entity related errors with codes and messages
 * @author Assaf Moldavsky
 */

public enum UserError implements ApiError {

	INTERNAL_ERROR( -1000, "internal error" ),
	INVALID_USERNAME( -1100, "username is invalid! the username must be alphanumeric and can only contain '.','-'" ),
	INVALID_FIRST_NAME( -1101, "first name is invalid" ),
	INVALID_LAST_NAME( -1102, "last name is invalid" ),
	DOES_NOT_EXIST( -1103, "user cannot be updated because user does not exist" ),
	EMAIL_CHANGE_NOT_ALLOWED( -1104, "email change is not allowed" );


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
