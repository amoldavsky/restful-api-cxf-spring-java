package com.projectx.web.api.service;

import javax.inject.Named;
import javax.ws.rs.*;

import com.projectx.sdk.api.ApiResponse;
import com.projetx.sdk.user.User;
import javax.ws.rs.core.MediaType;

/**
 * All user related API end-points. This interface takes care of all
 * of the CXF annotations so that the implementation remains clean and
 * agnostic of the framework.
 *
 * @author Assaf Moldavsky
 */
@JaxrsService
@Named
public interface UserRest {

    @Path("/user/{userId}")
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    ApiResponse<User> getUser(
            @PathParam("userId") String userId
    );

    @Path("/user")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces( MediaType.APPLICATION_JSON )
    ApiResponse<User> createUser( User user );

    /*
    @Path("/user/{userId}")
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces( MediaType.APPLICATION_JSON )
    ApiResponse<User> updateUser( @PathParam("userId") String userId, User user );

    @Path("/user/{userId}")
    @DELETE
    @Produces( MediaType.APPLICATION_JSON )
    void deleteUser(
            @PathParam("userId") String userId
    );
    */
}
