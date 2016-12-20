package com.projectx.logic.api.service.validation;

/**
 * Encapsulated errors related to a particular entity.
 * For instance all User entity errors.
 *
 * @author Assaf Moldavsky
 */

public interface ApiError {

	int getCode();
	String getMessage();

}
