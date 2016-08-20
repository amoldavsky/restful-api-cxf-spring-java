package com.projetx.sdk.user.impl;


import java.io.Serializable;
import java.util.Date;

/**
 * A User POJO basic implementation
 * 
 * @author Assaf Moldavsky
 */

public class BasicUser implements com.projetx.sdk.user.User,Serializable {

	private static final long serialVersionUID = 1L;
	
	Integer id;
	String firstName;
	String lastName;
	String email;
	String password;
	Date dateCreated;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}
