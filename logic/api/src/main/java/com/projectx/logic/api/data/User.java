package com.projectx.logic.api.data;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The API specific User pojo. This pojo is implementing the 
 * SDK pojo and adds annotations specific to the API project.
 * 
 * We want to explicitly use the SDK interfaces so that objects are 
 * serialized and deserialized in a safe manner for all applications.
 * 
 * @author Assaf Moldavsky
 *
 */
@Entity
@Table( name = "users" )
@SuppressWarnings("serial")
public class User implements com.projetx.sdk.user.User,Serializable {

	
	@Id
	@NotNull
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column
	@NotBlank
	String firstName;
	
	@Column
	@NotBlank
	String lastName;
	
	@Column
	@NotBlank
	String email;
	
	@Column
	@NotBlank
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