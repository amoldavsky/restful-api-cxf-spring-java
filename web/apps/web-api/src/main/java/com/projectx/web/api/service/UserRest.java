package com.projectx.web.api.service;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.projetx.sdk.user.User;

import javax.ws.rs.Produces;

import java.util.*;

/**
 * All user related API end-points. This interface takes care of all
 * of the CXF annotations so that the implementation remains clean and
 * agnostic of the framework.
 * 
 * @author Assaf Moldavsky
 *
 */
@JaxrsService
@Named
public interface UserRest {

	@Path("/user/{userId}")
	@GET
	@Produces("application/json")
	public User getUser(
			@PathParam("userId") String userId
	);
	
	@Path("/user")
	@POST
	@Produces("application/json")
	public User createUser(
			@QueryParam("firstName") String firstName,
			@QueryParam("lastName") String lastName,
			@QueryParam("email") String email,
			@QueryParam("password") String password
	);
	
}
