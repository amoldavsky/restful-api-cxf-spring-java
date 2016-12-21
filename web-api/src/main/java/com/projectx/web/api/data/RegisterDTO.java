package com.projectx.web.api.data;

import com.projectx.sdk.user.User;
import com.projectx.sdk.user.impl.BasicUser;

public class RegisterDTO extends BasicUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6131385841149481964L;

	public RegisterDTO( User user ) {
		super( user );
	}

	String password;
	
	public void setPassword( String password ) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	
}
