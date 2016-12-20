package com.projectx.web.api.service.rest.impl;

import java.util.*;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.google.common.base.Joiner;

import com.projectx.web.api.data.SearchDTO;
import com.projectx.web.api.data.UserDTO;
//import com.projectx.web.api.manager.UserManager;
//import com.projectx.web.api.service.Auth0AuthenticationService;
//import com.projectx.web.api.service.UserEnhanceService;
import com.projectx.web.api.service.rest.JaxrsService;
import com.projectx.web.api.service.rest.UserRest;

//import com.projectx.web.api.util.RestUtils;
import com.projectx.web.api.util.RestUtils;
import com.projectx.web.api.util.UserUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectx.logic.api.service.UserService;
import com.projectx.logic.api.service.util.ValueResponse;
import com.projectx.sdk.api.ApiResponse;
import com.projectx.web.api.util.BaseResponse;
import com.projectx.sdk.user.User;
import com.projectx.sdk.user.impl.BasicUser;
import com.projectx.sdk.user.impl.LoginCredentials;
import com.projectx.sdk.user.impl.UserSession;
import com.projectx.web.api.data.RegisterDTO;

/**
 * Actual implementation of the UserRest interface, which contains the
 * actual CXF annotations.
 *
 * @author Assaf Moldavsky
 *
 */
@JaxrsService
@Service("com.projectx.web.api.service.rest.UserRest")
public class UserRestImpl implements UserRest {

	protected static Logger log = LogManager.getLogger( UserRestImpl.class );

	@Autowired
	UserService userService;

//	@Autowired
//	UserEnhanceService userEnhanceService;

//	@Autowired
//	Auth0AuthenticationService auth0Service;

//	@Autowired
//	UserManager userManager;

	@Context // Instantiated by SessionFilter
			SecurityContext securityContext;

	// TODO: move this into a utility class
	private WebApplicationException generateError( int httpStatusCode, ApiResponse apiResponse ) {

		apiResponse.setResultCode( ApiResponse.FAILURE );
		javax.ws.rs.core.Response response = Response.status( httpStatusCode ).entity( apiResponse ).build();
		return new WebApplicationException( response );

	}

	@Override
	public ApiResponse<User> getUser( String userIdParam ) {

		if( userIdParam == null ) {

			if( log.isDebugEnabled() ) {
				log.debug( "START ( arguments: userId = " + userIdParam + " )");
			}

			return new ApiResponse<User>( ApiResponse.FAILURE, "user id is required, got id = " + userIdParam, null );

		}

		Integer userId = null;

		try {

			userId = Integer.valueOf( userIdParam );

		} catch( NumberFormatException nee ) {

			if( log.isDebugEnabled() ) {
				log.debug( "END ( arguments: userId = " + userIdParam + " )");
			}

			return new ApiResponse<>( ApiResponse.FAILURE, "user id is invalid, got id = " + userIdParam, null );

		}

		if( userId == null || userId <= 0 ) {

			if( log.isDebugEnabled() ) {
				log.debug( "END ( arguments: userId = " + userIdParam + " )");
			}

			return new ApiResponse<>( ApiResponse.FAILURE, "user id is invalid, got id = " + userIdParam, null );

		}

		ValueResponse<com.projectx.logic.api.data.User> userResp = userService.getUser( userId );
		if( userResp.isFailure() ) {
			return new ApiResponse<>( ApiResponse.FAILURE, userResp.getMessage(), null );
		}

		// TODO: return HTTP 404
		if( userResp.getValue() == null ) {
			return new ApiResponse<>( ApiResponse.FAILURE, "not found", null );
		}
		UserDTO newUSer = new UserDTO( userResp.getValue() );

		if( log.isDebugEnabled() ) {
			log.debug( "END ( arguments: userId = " + userIdParam + " )");
		}

		return new ApiResponse<>( ApiResponse.SUCCESS, "", newUSer );
	}

	@Override
	public ApiResponse<User> getUserByUsername( String usernameParam ) {

		if( StringUtils.isEmpty( usernameParam ) ) {

			if( log.isDebugEnabled() ) {
				log.debug( "START ( arguments: usernameParam = " + usernameParam + " )");
			}

			return new ApiResponse<User>( ApiResponse.FAILURE, "username is required", null );

		}

		ValueResponse<com.projectx.logic.api.data.User> userResp = userService.getUserByUsername( usernameParam );
		if( userResp.isFailure() ) {
			return new ApiResponse<User>( ApiResponse.FAILURE, userResp.getMessage(), null );
		}
		if( userResp.getValue() == null ) {
			return new ApiResponse<User>( ApiResponse.FAILURE, "user not found", null );
		}

		UserDTO newUSer = new UserDTO( userResp.getValue() );

		if( log.isDebugEnabled() ) {
			log.debug( "END ( arguments: userId = " + usernameParam + " )");
		}

		return new ApiResponse<>( ApiResponse.SUCCESS, "", newUSer );

	}

//	@Override
//	public ApiResponse<SearchDTO<UserDTO>> getUsers( final HashSet<Integer> userIds, final Integer page, final HashSet<String> packages ) {
//
//		ValueResponse<List<com.projectx.logic.api.data.User>> usersResp = null;
//
//		// if no list is given than we will be fetching all users
//		if( userIds == null || userIds.isEmpty() ) {
//			usersResp = getAllUsers( 1 );
//		} else {
//			usersResp = getUsersById( userIds, 1 );
//		}
//
//		if( !usersResp.isSuccess() || usersResp.getValue() == null ) {
//			String error = "internal error";
//
//			if( !StringUtils.isEmpty( usersResp.getMessage() ) ) {
//				error = usersResp.getMessage();
//			}
//
//			throw generateError(
//					HttpServletResponse.SC_PRECONDITION_FAILED,
//					new ApiResponse<SearchDTO<User>>(
//							ApiResponse.FAILURE,
//							error,
//							null
//					)
//			);
//		}
//
//		// it is possible that we got no results...
//		if( usersResp.getValue().isEmpty() ) {
//			return new ApiResponse<>(
//					ApiResponse.SUCCESS,
//					"",
//					new SearchDTO<>( Collections.emptyList() )
//			);
//		}
//
//		List<UserDTO> users = new ArrayList<>();
//		usersResp.getValue().forEach(
//				apiUser -> users.add( new UserDTO( apiUser ) )
//		);
//
//		Set<UserEnhanceService.Pack> packs = new HashSet<>();
//		if( packages != null && !packages.isEmpty() ) {
//			for (Iterator<String> iter = packages.iterator(); iter.hasNext(); ) {
//				String pack = iter.next();
//				if( "playbooks".equals( pack ) ) {
//					packs.add( UserEnhanceService.Pack.PLAYBOOKS );
//				}
//			}
//		}
//
//		ValueResponse<List<UserDTO>> enhanceResp = userEnhanceService.enhanceUsersWith( users, packs );
//		if( !enhanceResp.isSuccess() || enhanceResp.getValue() == null ) {
//			String error = "enhance error";
//
//			if( StringUtils.isEmpty( enhanceResp.getMessage() ) ) {
//				error = enhanceResp.getMessage();
//			}
//
//			throw generateError(
//					HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
//					new ApiResponse<>(
//							ApiResponse.FAILURE,
//							error,
//							null
//					)
//			);
//		}
//
//		return new ApiResponse<>(
//				ApiResponse.SUCCESS,
//				"",
//				new SearchDTO<>( enhanceResp.getValue() )
//		);
//	}

	private ValueResponse<List<com.projectx.logic.api.data.User>> getUsersById( Set<Integer> userIds, Integer page ) {

		if( userIds == null || userIds.isEmpty() ) {
			throw generateError(
					HttpServletResponse.SC_PRECONDITION_FAILED,
					new ApiResponse<SearchDTO<User>>(
							ApiResponse.FAILURE,
							"ids are required",
							null
					)
			);
		}

		return userService.getUsers( userIds );
	}

	private ValueResponse<List<com.projectx.logic.api.data.User>> getAllUsers( Integer page ) {

		if( page == null ) {
			page = 1;
		}

		return userService.getAllUsers( page, null, null );

	}

	@Override
	public ApiResponse<User> createUser( RegisterDTO user ) {

		if( user == null ) {
			return new ApiResponse<>( ApiResponse.FAILURE, "user is required, got user = " + user, null );
		}
		if( StringUtils.isEmpty( user.getEmail() ) ) { // TODO: add unit tests
			return new ApiResponse<>( ApiResponse.FAILURE, "user email is required, got user = " + user, null );
		}
		if( StringUtils.isEmpty( user.getPassword() ) ) { // TODO: add unit tests
			return new ApiResponse<>( ApiResponse.FAILURE, "user password is required, got user = " + user, null );
		}

		com.projectx.logic.api.data.User userNew = UserUtils.transformUser( user );
		userNew.setPassword( user.getPassword());

//		BaseResponse<com.projectx.logic.api.data.User> createResp = userManager.registerUser( userNew );
		ValueResponse<com.projectx.logic.api.data.User> createResp = userService.createUser( userNew );


		UserDTO responseUser = null;
		if( createResp.isSuccess() ) {
			responseUser = new UserDTO( createResp.getValue() );
		}

		return new ApiResponse<>( createResp.getResultCode(), createResp.getMessage(), responseUser );
	}

	// TODO: add unit tests for this method
	@Override
	public ApiResponse<UserSession> loginUser( LoginCredentials login ) {

		if( login == null ) {
			return new ApiResponse<>( ApiResponse.FAILURE, "login data is required", null );
		}
		if( StringUtils.isEmpty( login.getLogin() )  ) { // TODO: add unit tests
			return new ApiResponse<>( ApiResponse.FAILURE, "username or email is required", null );
		}
		if( StringUtils.isEmpty( login.getPassword() ) ) { // TODO: add unit tests
			return new ApiResponse<>( ApiResponse.FAILURE, "password is required", null );
		}

//		BaseResponse<UserSession> authResp = userManager.authenticateUser( login );
		ValueResponse<UserSession> authResp = new ValueResponse( 1, "moo!", new UserSession() );

		return new ApiResponse<>( authResp.getResultCode(), authResp.getMessage(), authResp.getValue() );
	}

	@Override
	public ApiResponse<User> updateUser( String userIdStr, User user ) {

		if( userIdStr == null || StringUtils.isEmpty( userIdStr ) ) {
			return new ApiResponse<User>( ApiResponse.FAILURE, "user id is required, got id = " + userIdStr, null );
		}
		if( user == null ) {
			return new ApiResponse<User>( ApiResponse.FAILURE, "user is required, got user = " + user, null );
		}

		Integer userId = null;
		try {
			userId = Integer.valueOf( userIdStr );
		} catch( Throwable t ) {
			throw generateError( HttpServletResponse.SC_FORBIDDEN, new ApiResponse<User>( ApiResponse.FAILURE, "invalid user id", null ) );
		}

		if( userId != user.getId() ) {
			throw generateError( HttpServletResponse.SC_FORBIDDEN, new ApiResponse<User>( ApiResponse.FAILURE, "not alloed", null ) );
		}

		ValueResponse<com.projectx.logic.api.data.User> userResp = null;
		try {
			userResp = userService.updateUser((com.projectx.logic.api.data.User) user);
		} catch( IllegalArgumentException iae ) {
			return new ApiResponse<>( ApiResponse.FAILURE, iae.getMessage(), null );
		} catch( Throwable t ) {
			t.printStackTrace();
		}

		if( userResp.isFailure() || userResp.getValue() == null ) {

			return new ApiResponse<>( ApiResponse.FAILURE, userResp.getMessage(), null );

		}

		return new ApiResponse<>(ApiResponse.SUCCESS, "",  new UserDTO( userResp.getValue() ) );
	}

	@Override
	public ApiResponse<Integer> deleteUser( String userIdStr ) {

		Integer userId = RestUtils.validateIntParam( userIdStr, "user id is required" );

//		BaseResponse<com.projectx.logic.api.data.User> deleteResp = userManager.deleteUser( userId );
		ValueResponse<com.projectx.logic.api.data.User> deleteResp = userService.deleteUser( userId );

		if( !deleteResp.isSuccess() || deleteResp.getValue() == null ) {

			String error = "internal error";

			if( StringUtils.isEmpty( deleteResp.getMessage() ) ) {
				error = deleteResp.getMessage();
			}

			throw generateError(
					HttpServletResponse.SC_PRECONDITION_FAILED,
					new ApiResponse<SearchDTO<User>>(
							ApiResponse.FAILURE,
							error,
							null
					)
			);
		}

		return new ApiResponse<> (
				ApiResponse.SUCCESS,
				"",
				deleteResp.getValue().getId()
		);
	}

}
