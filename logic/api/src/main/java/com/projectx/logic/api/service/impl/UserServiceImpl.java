package com.projectx.logic.api.service.impl;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolationException;

import org.apache.cxf.common.util.StringUtils;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projectx.util.crypto.AES;
import com.projectx.logic.api.dao.UserDAO;
import com.projectx.logic.api.data.User;
import com.projectx.logic.api.service.UserService;
import com.projectx.logic.api.service.util.ValueResponse;
import com.projectx.logic.api.service.validation.UserError;

/**
 *
 * @author Assaf Moldavsky
 *
 */
@Service( "com.kur8or.logic.api.service.UserService" )
public class UserServiceImpl implements UserService {

	private final String ENCRYPTION_KEY = "faJ3uEh4HuMuuVzC";

	@Autowired
	UserDAO userDAO;

	private final AES aes = new AES( ENCRYPTION_KEY );

	@Transactional(readOnly = true)
	public ValueResponse<User> getUser( Integer userId ) {

		if( userId == null ) throw new IllegalArgumentException( "userId is required" );

		User user = userDAO.getUser( userId );

		if( user == null ) {
			return new ValueResponse<User>( ValueResponse.FAILURE, "not found", user );
		}

		return new ValueResponse<User>( ValueResponse.SUCCESS, "", user );

	}

	@Transactional(readOnly = true)
	public ValueResponse<User> getUser( String emailOrUsername ) {

		if( StringUtils.isEmpty( emailOrUsername ) ) {
			throw new IllegalArgumentException( "email or username is required" );
		}

		if( emailOrUsername.contains( "@" ) ) {
			return this.getUserByEmail( emailOrUsername );
		} else {
			return this.getUserByUsername( emailOrUsername );
		}

	}

	@Transactional(readOnly = true)
	public ValueResponse<User> getUserByEmail( String email ) {

		if( StringUtils.isEmpty( email ) ) {
			throw new IllegalArgumentException( "email is required" );
		}

		User user = userDAO.getUserByEmail( email );

		return new ValueResponse<User>( ValueResponse.SUCCESS, "", user );

	}

	@Transactional(readOnly = true)
	public ValueResponse<User> getUserByUsername( String username ) {

		if( StringUtils.isEmpty( username ) ) {
			throw new IllegalArgumentException( "username is required" );
		}

		User user = userDAO.getUserByUsername( username );

		return new ValueResponse<User>( ValueResponse.SUCCESS, "", user );

	}

	public ValueResponse<User> decryptPassword( User user ) {

		if( user == null ) throw new IllegalArgumentException( "user are required" );

		ValueResponse<User> response = new ValueResponse<User>( ValueResponse.SUCCESS, "", user );

		if( !StringUtils.isEmpty( user.getPassword() ) ) {

			try {
				user.setPassword( aes.decrypt( user.getPassword() ) );
			} catch (Exception e) {
				response.setResultCode( ValueResponse.FAILURE );
				response.setMessage( "unable to decrypt user's password" );
			}
		}

		return response;

	}


	@Transactional(readOnly = true)
	public ValueResponse<List<User>> getUsers( Set<Integer> userIds ) {

		if( userIds == null || userIds.isEmpty() ) throw new IllegalArgumentException( "userIds are required" );

		return getUsers( userIds, 10 );

	}

	//TODO - discuss what is the use of this
	@Transactional(readOnly = true)
	public ValueResponse<List<User>> getUsers( Set<Integer> userIds, int limit) {

		if( userIds == null ) throw new IllegalArgumentException( "userIds are required" );

		List<User> users = null;

		try {
			users = userDAO.getUsers( userIds, limit );
		} catch ( Throwable t ) {
			return new ValueResponse<>(
					ValueResponse.FAILURE,
					"internal error",
					null
			);
		}

		return new ValueResponse<List<User>>( ValueResponse.SUCCESS, "", users );

	}

	@Override
	@Transactional( readOnly = true )
	public ValueResponse<List<User>> getAllUsers( Integer page, Integer rpp, Integer limit ) {

		if( page == null || page < 0 ) throw new IllegalArgumentException( "page is required" );
		if( rpp == null || rpp < 1 ) {
			rpp = 25;
		}
		if( limit == null || limit < 1 ) {
			limit = 100;
		}

		List<User> users = null;

		try {
			users = userDAO.getAllUsers( page - 1, rpp, limit );
		} catch ( Throwable t ) {
			return new ValueResponse<>(
					ValueResponse.FAILURE,
					"internal error",
					null
			);
		}

		return new ValueResponse<List<User>>( ValueResponse.SUCCESS, "", users );

	}

	@Transactional
	public ValueResponse<User> createUser( User user ) {

		if( user == null ) throw new IllegalArgumentException( "user is required" );

		ZonedDateTime utc = ZonedDateTime.now( ZoneOffset.UTC );
		user.setDateCreated( Date.from( utc.toInstant() ) );

		if( !validEmail( user.getEmail() ) ) {
			return new ValueResponse<User>( ValueResponse.FAILURE, "email is invalid", null );
		}
		if( StringUtils.isEmpty( user.getUsername() ) ) {
			user.setUsername( generateUsername() );
		}
		if( !StringUtils.isEmpty( user.getFirstName() ) && !validName( user.getFirstName() ) ) {
			return new ValueResponse<User>( ValueResponse.FAILURE, "first name is invalid", null );
		}
		if( !StringUtils.isEmpty( user.getLastName() ) && !validName( user.getLastName() ) ) {
			return new ValueResponse<User>( ValueResponse.FAILURE, "last name is invalid", null );
		}
		if( !StringUtils.isEmpty( user.getUsername() ) && !validUsername( user.getUsername() ) ) {
			return new ValueResponse<User>( ValueResponse.FAILURE, "username is invalid", null );
		}

		try {
			user.setPassword( aes.encrypt( user.getPassword() ) );
		} catch ( Exception e ) {
			return new ValueResponse<User>( ValueResponse.FAILURE, e.getMessage() );
		}

		try {
			userDAO.createUser( user );
		} catch( ConstraintViolationException cve ) {

			return new ValueResponse<User>( ValueResponse.FAILURE, cve.getMessage() );

		} catch( SQLGrammarException sge ) {

			return new ValueResponse<User>( ValueResponse.FAILURE, sge.getMessage() );

		} catch( Throwable t ) {

			return new ValueResponse<User>( ValueResponse.FAILURE, t.toString() );
		}

		return new ValueResponse<User>( ValueResponse.SUCCESS, "", user );

	}


	@Transactional
	public ValueResponse<User> deleteUser( Integer userId ) {
		if( userId == null ) throw new IllegalArgumentException( "userId is required" );

		ValueResponse<User> userResp = getUser( userId );

		if( userResp.isFailure() || userResp.getValue() == null ) {
			return new ValueResponse<User>( ValueResponse.FAILURE, "user cannot be deleted because user does not exist", null );
		}

		User user = userResp.getValue();
		user = userDAO.deleteUser( user );

		return new ValueResponse<User>( ValueResponse.SUCCESS, "", user );
	}


	public ValueResponse<User> updateUser( User user ) {

		if( user == null ) throw new IllegalArgumentException( "user is required" );
		if( user.getId() == null ) throw new IllegalArgumentException( String.format( "userId is required, got id = %s", user.getId() ) );

		if( StringUtils.isEmpty( user.getUsername() ) ) {
			user.setUsername( generateUsername() );
		}

		if( !StringUtils.isEmpty( user.getFirstName() ) && !validName( user.getFirstName() ) ) {
			return new ValueResponse<>( UserError.INVALID_FIRST_NAME );
		}
		if( !StringUtils.isEmpty( user.getLastName() ) && !validName( user.getLastName() ) ) {
			return new ValueResponse<>( UserError.INVALID_LAST_NAME );
		}
		if( !StringUtils.isEmpty( user.getUsername() ) && !validUsername( user.getUsername() ) ) {
			return new ValueResponse<>( UserError.INVALID_USERNAME );
		}

		ValueResponse<User> userResp = getUser( user.getId() );

		if( userResp.isFailure() || userResp.getValue() == null ) {
			return new ValueResponse<>( UserError.DOES_NOT_EXIST );
		}

		User savedUser = userResp.getValue();
		// TODO: review this later
//		if( !savedUser.getEmail().equals( user.getEmail() ) ) { // do not let user to chaneg emails
//			return new ValueResponse<>( UserError.EMAIL_CHANGE_NOT_ALLOWED );
//		}
		if( !savedUser.getEmail().equals( user.getEmail() ) ) { // do not let user to chaneg emails
			user.setEmail( savedUser.getEmail() );
		}
		user.setPassword( savedUser.getPassword() );

		userDAO.updateUser( user );
		return new ValueResponse<>( ValueResponse.SUCCESS, "", user );
	}

	private String generateUsername() {

		ZonedDateTime utc = ZonedDateTime.now( ZoneOffset.UTC );
		String usernameSuffix = String.valueOf( utc.toEpochSecond() * 1000 );
		return String.format( "user-%s", usernameSuffix );

	}

	public static final String VALID_NAME_REGEXP = "^[a-z ,.'-]+$";
	public static final Pattern VALID_NAME_REGEXP_PATTERN = Pattern.compile( VALID_NAME_REGEXP, Pattern.CASE_INSENSITIVE );;
	public static final String VALID_USERNAME_REGEXP = "^[a-z0-9\\.-]{3,36}$";
	public static final Pattern VALID_USERNAME_REGEXP_PATTERN = Pattern.compile( VALID_USERNAME_REGEXP, Pattern.CASE_INSENSITIVE );;
	public static final String VALID_EMAIL_REGEXP = "^[_A-Z0-9-\\+]+(\\.[_A-Z0-9-]+)*@[A-Z0-9-]+(\\.[A-Z0-9]+)*(\\.[A-Z]{2,})$";
	public static final Pattern VALID_EMAIL_REGEXP_PATTERN = Pattern.compile( VALID_EMAIL_REGEXP, Pattern.CASE_INSENSITIVE );;

	public static boolean validUsername( String name ) {
		if( StringUtils.isEmpty( name ) ) return false;
		return VALID_USERNAME_REGEXP_PATTERN.matcher( name ).matches();
	}

	public static boolean validName( String name ) {
		if( StringUtils.isEmpty( name ) ) return false;
		return VALID_NAME_REGEXP_PATTERN.matcher( name ).matches();
	}

	public static boolean validEmail( String email ) {
		if( StringUtils.isEmpty( email ) ) return false;
		return VALID_EMAIL_REGEXP_PATTERN.matcher( email ).matches();
	}

}