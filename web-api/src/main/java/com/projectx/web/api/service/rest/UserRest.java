package com.projectx.web.api.service.rest;

import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.Produces;

import com.projectx.sdk.api.ApiResponse;
import com.projectx.sdk.user.User;
import com.projectx.sdk.user.impl.LoginCredentials;
import com.projectx.sdk.user.impl.UserSession;
import com.projectx.web.api.data.RegisterDTO;
import com.projectx.web.api.data.SearchDTO;
import com.projectx.web.api.data.UserDTO;
import com.projectx.web.api.service.Secured;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

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
@CrossOriginResourceSharing(
		allowAllOrigins = true,
        allowCredentials = true, 
        maxAge = 1209600
//        allowHeaders = {
//        		"origin, content-type, accept, authorization"
//        },
//        exposeHeaders = {
//        		"origin, content-type, accept, authorization"
//        }
)
public interface UserRest {

    @Path("/user/{userId}")
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public ApiResponse<User> getUser(
            @PathParam("userId") String userId
    );

    @Path("/user")
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public ApiResponse<User> getUserByUsername(
            @QueryParam("username") String username
    );

    @Path("/user")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces( MediaType.APPLICATION_JSON )
    public ApiResponse<User> createUser( RegisterDTO user );

    @Path("/user/login")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces( MediaType.APPLICATION_JSON )
    public ApiResponse<UserSession> loginUser( LoginCredentials user );

    @Path("/user/{userId}")
    @PUT
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, })
    @Produces( MediaType.APPLICATION_JSON )
    public ApiResponse<User> updateUser( @PathParam("userId") String userId, User user );

    @Path("/user/{userId}")
    @DELETE
    @Produces( MediaType.APPLICATION_JSON )
    public ApiResponse<Integer> deleteUser(
            @PathParam("userId") String userId
    );

    /*@Path("/users")
    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public ApiResponse<SearchDTO<UserDTO>> getUsers(
            @QueryParam("id") final HashSet<Integer> userIds,
            @QueryParam("page") final Integer page,
            @QueryParam("package") final HashSet<String> packages
    );*/
}
