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
@SuppressWarnings( "serial" )
public class User implements Serializable {

	@Id
	@NotNull
	@Column( unique = true, nullable = false )
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column( name = "profile_image_url", nullable = true )
	String profileImageUrl;

	@Column( name = "username", nullable = true )
	String username;

	@Column( name = "first_name" )
	//@NotBlank
			String firstName;

	@Column( name = "last_name" )
	//@NotBlank
			String lastName;

	@Column( name = "email", unique = true )
	@NotBlank
	String email;

	@Column
	@NotBlank
	String password;

	@Column( name = "date_created" )
	Date dateCreated;

	public User() {

	}
	public User( User user ) {

		setId( user.getId() );
		setProfileImageUrl( user.getProfileImageUrl() );
		setUsername( user.getUsername() );
		setFirstName( user.getFirstName() );
		setLastName( user.getLastName() );
		setEmail( user.getEmail() );
		setPassword( user.getPassword() );
		setDateCreated( user.getDateCreated() );

	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	public void setProfileImageUrl( String url ) { this.profileImageUrl = url; }

	public void setUsername( String username ) { this.username = username; }
	public String getUsername() {
		return username;
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

	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}