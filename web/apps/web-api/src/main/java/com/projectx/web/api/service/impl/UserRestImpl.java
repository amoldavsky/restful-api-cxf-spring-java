package com.projectx.web.api.service.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.logic.api.service.UserService;
import com.projectx.web.api.service.JaxrsService;
import com.projectx.web.api.service.UserRest;
import com.projetx.sdk.user.User;
import com.projetx.sdk.user.impl.BasicUser;

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
	public User getUser( String userIdParam ) {
		
		Integer userId = null;
		
		try {
			
			userId = Integer.valueOf( userIdParam );
			
		} catch( NumberFormatException nee ) {
			
			// do nothing for now
			
		}
		
		if( userId == null || userId <= 0 ) {
			return null;
		}
		
		return userService.getUser( userId );
	}
	
	@Override
	public User createUser(String firstName, String lastName, String email, String password) {
		User user = new BasicUser();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		userService.createUser((com.projectx.logic.api.data.User) user);
		return user;
	}
	
}
