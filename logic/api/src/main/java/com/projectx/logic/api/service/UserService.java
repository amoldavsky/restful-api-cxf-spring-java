package com.projectx.logic.api.service;


import java.util.List;
import java.util.Set;

import com.projectx.logic.api.data.User;
import com.projectx.logic.api.service.util.ValueResponse;
//import com.projectx.sdk.user.impl.UserSession;

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
	public ValueResponse<User> getUser( Integer userId );

	/**
	 * Returns a single {@link com.projectx.logic.api.data.User} given an email address or a username.
	 *
	 * @param emailOrUsername	a user's email address or username
	 * @return					a single {@link com.projectx.logic.api.data.User}
	 */
	public ValueResponse<User> getUser( String emailOrUsername );

	/**
	 * Returns a single {@link com.projectx.logic.api.data.User} given an email address.
	 *
	 * @param email	a user's email address
	 * @return		a single {@link com.projectx.logic.api.data.User}
	 */
	public ValueResponse<User> getUserByEmail( String email );

	/**
	 * Decrypts the user's password stored in the POJO
	 *
	 * @param user
	 * @return
	 */
	public ValueResponse<User> decryptPassword( User user );

	/**
	 * Returns a single {@link com.projectx.logic.api.data.User} given a username.
	 *
	 * @param username	the username
	 * @return			a single {@link com.projectx.logic.api.data.User}
	 */
	public ValueResponse<User> getUserByUsername( String username );

	/**
	 * Returns a List of {@link com.projectx.logic.api.data.User} given a List of user ids.
	 * The order of result will remain the same as the order of passed in ids.
	 *
	 * @param userIds	a Set of user ids
	 * @return			a List of {@link com.projectx.logic.api.data.User}
	 */
	public ValueResponse<List<User>> getUsers( Set<Integer> userIds );

	/**
	 * Returns a List of {@link com.projectx.logic.api.data.User} given a List of user ids.
	 * The order of result will remain the same as the order of passed in ids.
	 *
	 * @param userIds	a Set of user ids ( for uniqueness )
	 * @param limit		upper limit for the number of records to return
	 * @return			a List of {@link com.projectx.logic.api.data.User}
	 */
	public ValueResponse<List<User>> getUsers( Set<Integer> userIds, int limit );

	/**
	 * Returns a List of {@link com.projectx.logic.api.data.User} given a List of user ids.
	 * The order of result will remain the same as the order of passed in ids.
	 *
	 * @param page		the page number
	 * @param rpp		results per page
	 * @param limit		the max number of results to fetch
	 * @return			a List of {@link com.projectx.logic.api.data.User}
	 */
	public ValueResponse<List<User>> getAllUsers( Integer page, Integer rpp, Integer limit );

	/**
	 * Creates a single {@link com.projectx.logic.api.data.User} by persisting it to 
	 * the datastore.
	 *
	 * After persisting to datastore the user id will be updated.
	 *
	 * @param user		a User Object
	 * @return			a single {@link com.projectx.logic.api.data.User} which is no persisted to datastore
	 */
	public ValueResponse<User> createUser( User user );

	/**
	 * Deletes a single {@link com.projectx.logic.api.data.User}
	 *
	 * @param userId	a user id
	 * @return			the deleted user id
	 */
	public ValueResponse<User> deleteUser( Integer userId );

	/**
	 * updates a single user
	 *
	 * @param user
	 */
	public ValueResponse<User> updateUser(User user);

}
