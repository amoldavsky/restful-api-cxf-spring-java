package com.projectx.logic.api.service;


import com.projectx.logic.api.data.User;

/**
 * Service to deal with all User manipulation and persisntance
 * 
 * @author Assaf Moldavsky
 *
 */
public interface UserService {
	
	/**
	 * Returns a single {@link com.projectx.logic.api.data.User} given an id.
	 * 
	 * @param userId	a user id
	 * @return			a single {@link com.projectx.logic.api.data.User}
	 */
	public User getUser( Integer userId );
	
	/**
	 * Creates a single {@link com.projectx.logic.api.data.User} by persisting it to 
	 * the datastore.
	 * 
	 * After persisting to datastore the user id will be updated.
	 * 
	 * @param userId	a User Object
	 * @return			a single {@link com.projectx.logic.api.data.User} which is no persisted to datastore
	 */
	public User createUser( User user );
	
}
