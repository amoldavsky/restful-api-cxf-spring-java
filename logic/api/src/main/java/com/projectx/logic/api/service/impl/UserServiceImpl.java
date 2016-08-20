package com.projectx.logic.api.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectx.logic.api.dao.UserDAO;
import com.projectx.logic.api.data.User;
import com.projectx.logic.api.service.UserService;
import com.projetx.sdk.user.impl.BasicUser;

/**
 * 
 * @author Assaf Moldavsky
 *
 */
@Service("com.projectx.logic.api.service.UserService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;
	
	private User createDummyUser() {
		
		// create a dummy user object
		User user = new User();
		//user.setId( 007 );
		user.setFirstName( "James" );
		user.setLastName( "Bond" );
		user.setPassword( "007" );
		user.setEmail( "james.bond@testing.com" );
		user.setDateCreated( new Date() );
		
		userDAO.createUser( user );
		
		return user;
	}
	
	@Override
	@Transactional(readOnly = true)
	public User getUser(Integer userId) {
		
		if( userId == null ) throw new IllegalArgumentException( "userId is required" );
		
		User user = userDAO.getUser( userId );
		
		return user;
		
	}

	@Override
	@Transactional
	public User createUser(User user) {

		return this.createDummyUser();
		
	}
	
}
