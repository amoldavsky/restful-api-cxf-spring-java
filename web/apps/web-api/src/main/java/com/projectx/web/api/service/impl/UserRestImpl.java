package com.projectx.web.api.service.impl;

import com.projectx.sdk.api.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.logic.api.service.UserService;
import com.projectx.web.api.service.JaxrsService;
import com.projectx.web.api.service.UserRest;
import com.projetx.sdk.user.User;

/**
 * Actual implementation of the UserRest interface, which contains the
 * actual CXF annotations.
 * 
 * @author Assaf Moldavsky
 *
 */
@JaxrsService
@Service("com.projectx.web.api.service.UserRest")
public class UserRestImpl implements UserRest {
	
	@Autowired
	UserService userService;
	
	@Override
	public ApiResponse<User> getUser( String userIdParam ) {
		
		Integer userId = null;
		
		try {
			
			userId = Integer.valueOf( userIdParam );
			
		} catch( NumberFormatException nee ) {
			
			// do nothing for now
			
		}
		
		if( userId == null || userId <= 0 ) {
			return null;
		}
		
		User user = userService.getUser( userId );
		return new ApiResponse<>( ApiResponse.SUCCESS, "test user", user );
	}
	
	@Override
	public ApiResponse<User> createUser(User user ) {
		return new ApiResponse<>( ApiResponse.SUCCESS, "test user", user );
	}
	
}
