package com.projetx.sdk.user;

import java.util.Date;

/**
 * A User POJO interface
 * 
 * @author Assaf Moldavsky
 */
public interface User {

	public Integer getId();
	public void setId(Integer id);
	public String getFirstName();
	public void setFirstName(String firstName);
	public String getLastName();
	public void setLastName(String lastName);
	public String getEmail();
	public void setEmail(String email);
	public String getPassword();
	public void setPassword(String password);
	public Date getDateCreated();
	public void setDateCreated(Date dateCreated);
	
}
