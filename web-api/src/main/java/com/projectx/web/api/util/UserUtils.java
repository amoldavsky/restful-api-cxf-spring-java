package com.projectx.web.api.util;

import com.projectx.logic.api.data.User;
import com.projectx.web.api.data.RegisterDTO;
import com.projectx.web.api.data.UserDTO;

/**
 * All kind kinf of utils for handling the User pojo
 *
 * @author Assaf Moldavksy
 */
public class UserUtils {

	// TODO: add unit tests
	public static User transformUser( UserDTO userDTO ) {
		User apiUser = new User();

		apiUser.setId( userDTO.getId() );
		apiUser.setProfileImageUrl( userDTO.getProfileImageUrl() );
		apiUser.setUsername( userDTO.getUsername() );
		apiUser.setFirstName( userDTO.getFirstName() );
		apiUser.setLastName( userDTO.getLastName() );
		apiUser.setEmail( userDTO.getEmail() );
		apiUser.setDateCreated( userDTO.getDateCreated() );

		return apiUser;
	}

	// TODO: add unit tests
	public static User transformUser( RegisterDTO registerDTO ) {
		User apiUser = new User();

		apiUser.setId( registerDTO.getId() );
		apiUser.setProfileImageUrl( registerDTO.getProfileImageUrl() );
		apiUser.setUsername( registerDTO.getUsername() );
		apiUser.setFirstName( registerDTO.getFirstName() );
		apiUser.setLastName( registerDTO.getLastName() );
		apiUser.setEmail( apiUser.getEmail() );
		apiUser.setPassword( apiUser.getPassword() );
		apiUser.setDateCreated( registerDTO.getDateCreated() );

		return apiUser;
	}
}
