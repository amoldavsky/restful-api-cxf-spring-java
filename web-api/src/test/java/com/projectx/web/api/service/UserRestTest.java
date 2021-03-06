package com.projectx.web.api.service.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.lang.System;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.projectx.web.api.BaseTest;
import com.projectx.web.api.service.rest.UserRest;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.transport.local.LocalConduit;
import org.apache.cxf.jaxrs.client.WebClient;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.projectx.logic.api.service.UserService;
import com.projectx.web.api.test.config.spring.AppTestConfig;
//import com.projectx.sdk.user.User;
import com.projectx.logic.api.data.User;
import com.projectx.sdk.api.ApiResponse;
import com.projectx.sdk.user.impl.BasicUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		loader = AnnotationConfigContextLoader.class,
		classes = { UserRestTest.class, AppTestConfig.class }
)
@PowerMockIgnore( {"javax.management.*"})
@WebAppConfiguration
//@BootstrapWith(WebTestContextBootstrapper.class)
public class UserRestTest extends BaseTest {

	@Autowired
	UserService userService;

	@Autowired
	Server jaxRsServer;

	WebClient webClient;
	UserRest userRestRS;
	ObjectMapper objectMapper;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks( this );
		/*
		userService = Mockito.mock( UserService.class );
		userRestRS = Mockito.spy( new UserRestImpl() );
		ReflectionTestUtils.setField( userRestRS, "userService", userService );
		*/

		final List<Object> providers = new ArrayList<Object>();
		JacksonJaxbJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider();
		providers.add( jacksonJsonProvider );

		// custom deserializers for our abstract types ( User and Collection )
		this.objectMapper = jacksonJsonProvider.locateMapper(null, MediaType.APPLICATION_JSON_TYPE);
//		SimpleModule module = new SimpleModule(
//				"Kur8orApiDeserializer",
//				new Version(1, 0, 0, null, "", "")
//		);
//		module.addDeserializer( com.projectx.sdk.user.User.class, userAbstractDeserializer );
//		module.addDeserializer( Collection.class, collectionAbstractDeserializer );
//
//		objectMapper.registerModule( module );

		webClient = WebClient.create( "http://localhost:8080/api", providers );
		WebClient.getConfig( webClient ).getRequestContext().put( LocalConduit.DIRECT_DISPATCH, Boolean.TRUE );
		webClient.accept( "application/json" );

	}

	@After
	public void tearDown() throws Exception {

	}

	@Configuration
	@ComponentScan(
			basePackages = {
					"com.projectx.web.api.test",
					"com.projectx.web.api.service"
			}
	)
	public static class TestContextConfig {

	}

	@Test
	public void testGettersSetters() {



	}

	/**
	 * Tests the the /user/id endpoint for a valid id
	 */
	@Test
	public void testGetUserValidId() {
		//Mockito.doReturn( null ).when( userService ).getUser( 11 );

		User dummyUser = createDummyAPIUser();
		dummyUser = userService.createUser( dummyUser ).getValue();

		System.out.println( "testGetUserValidId: injected a new user with id " + dummyUser.getId() );
		Integer testUserId = dummyUser.getId();

		//Server server = this.jaxRsServer;

		webClient.path( "/user/" + testUserId );

		com.projectx.sdk.user.User reponseUser = null;

		try {

			String response = webClient.get( String.class );
			System.out.println( "testGetUserValidId: " + ( "'/user/" + testUserId + "' ") + "response: " + response );
			ApiResponse<com.projectx.sdk.user.User> apiResponse = objectMapper.readValue(
					response,
					objectMapper.getTypeFactory().constructType( new TypeReference<ApiResponse<User>>(){} )
			);

			reponseUser = apiResponse.getData();

		} catch( Exception e ) {

			e.printStackTrace();
			fail( e.getMessage() );
			return;

		}

		assertEquals( reponseUser.getId(), testUserId );
	}

	/**
	 * Tests the the /user/id endpoint for a negative id
	 */
	@Test
	public void testGetUserInvalidIdNegative() {
		//Mockito.doReturn( null ).when( userService ).getUser( 11 );

		com.projectx.sdk.user.User reponseUser = null;

		// test for a negative int
		Integer testUserId = -1;
		webClient.path( "/user/" + testUserId );

		ApiResponse<User> apiResponse = webClient.get( new GenericType<ApiResponse<User>>(){} );

		if( apiResponse.isSuccess() ) {
			fail( "was able to call /user/" + testUserId );
		}

		assertTrue( true );
	}

	/**
	 * Tests the the /user/id endpoint for an alphanumeric id
	 */
	@Test
	public void testGetUserInvalidIdAlphanumeric() {

		com.projectx.sdk.user.User reponseUser = null;

		// test a latter in the id
		String badUserId = "123abc";
		webClient.path( "/user/" + badUserId );


		ApiResponse<User> apiResponse = webClient.get( new GenericType<ApiResponse<User>>(){} );

		if( apiResponse.isSuccess() ) {
			fail( "was able to call /user/" + badUserId );
		}

		assertTrue( true );
	}

	@Test
	public void testCreateUser() {

		com.projectx.sdk.user.User newUser = createDummySDKUser();
		newUser.setId( null );

		webClient.path( "/user" );
		com.projectx.sdk.user.User reponseUser = null;

		try {

			System.out.println( "testCreateUser: POST request to " + "/user" );

			System.out.println( "sending: " + (new ObjectMapper()).writeValueAsString( newUser ));
			Response response = webClient.type( MediaType.APPLICATION_JSON ).post( newUser );
			String responseStr = response.readEntity( String.class );

			ApiResponse<com.projectx.sdk.user.User> apiResponse = objectMapper.readValue(
					responseStr,
					objectMapper.getTypeFactory().constructType( new TypeReference<ApiResponse<User>>(){} )
			);

			System.out.println( "testCreateUser: /user endpoint response: " + responseStr );
			reponseUser = apiResponse.getData();

		} catch( Exception e ) {

			e.printStackTrace();
			return;

		}

		assertNotNull( reponseUser.getId() );
		assertEquals( newUser.getFirstName(), reponseUser.getFirstName() );
		assertEquals( newUser.getLastName(), reponseUser.getLastName() );
		assertEquals( newUser.getEmail(), reponseUser.getEmail() );

	}

	@Test
	public void testUpdateUser() {

		User aUser = createDummyAPIUser();
		aUser = userService.createUser( aUser ).getValue();

		String apiUrl = "/user/" + aUser.getId();

		webClient.path( apiUrl );
		com.projectx.sdk.user.User initialUser = null;

		System.out.println( "testUpdateUser: GET request to " + apiUrl );
		ApiResponse<com.projectx.sdk.user.User> apiResponse = webClient.get( new GenericType<ApiResponse<com.projectx.sdk.user.User>>(){} );

		initialUser = apiResponse.getData();
		System.out.println( "testUpdateUser: " + apiUrl + " response: " + initialUser.toString() );

		assertNotNull( initialUser );

		apiUrl = "/user/" + initialUser.getId();
		webClient.reset().path( apiUrl );
		com.projectx.sdk.user.User updatedUser = null;
		String newFirstNAme = "a cool new name";

		try {

			// copy the initialUSer object into a new User object
			com.projectx.sdk.user.User tempUSer = new BasicUser( initialUser );
			tempUSer.setFirstName( newFirstNAme );

			System.out.println( "testUpdateUser: PUT request to " + apiUrl );
			ApiResponse<com.projectx.sdk.user.User> apiCheckResponse = webClient.type( MediaType.APPLICATION_JSON ).put( tempUSer, new GenericType<ApiResponse<com.projectx.sdk.user.User>>(){} );

			updatedUser = apiCheckResponse.getData();
			System.out.println( "testUpdateUser: " + apiUrl + " response: " + apiCheckResponse.toString() );

		} catch( Exception e ) {

			e.printStackTrace();
			fail();
			return;

		}

		assertNotNull( updatedUser );
		assertNotEquals( initialUser.getFirstName(), updatedUser.getFirstName() );
		assertEquals( updatedUser.getFirstName(), newFirstNAme );

		assertEquals( initialUser.getDateCreated(), updatedUser.getDateCreated() );
		assertEquals( initialUser.getLastName(), updatedUser.getLastName() );
		assertEquals( initialUser.getEmail(), updatedUser.getEmail() );
		assertEquals( initialUser.getId(), updatedUser.getId() );

	}

	@Test
	public void testDeleteUser() {

		User aUser = createDummyAPIUser();
		aUser = userService.createUser( aUser ).getValue();

		webClient.path( "/user/" + aUser.getId() );

		com.projectx.sdk.user.User reponseUser = null;

		try {

			Response response = webClient.delete( );
			String responseStr = response.readEntity( String.class );
			reponseUser = (new ObjectMapper()).readValue( responseStr, BasicUser.class );

		} catch( Exception e ) {

			//e.printStackTrace();
			assertTrue( true );
			return;

		}

		aUser = userService.getUser( reponseUser.getId() ).getValue();

		assertNull( aUser );

	}

	/**
	 * Tests the the /users?id=1&id=2&id=3 endpoint for a valid set of id's
	 */
	@Test
	public void testGetUsers() {

		List<Integer> userIds = new ArrayList<Integer>();

		User dummyUSer = createDummyAPIUser();
		dummyUSer = userService.createUser( dummyUSer ).getValue();
		userIds.add( dummyUSer.getId() );

		System.out.println( "testGetMulitpleUsers: injected a new user with id " + dummyUSer.getId() );

		User dummyUSer2 = createDummyAPIUser();
		dummyUSer2 = userService.createUser( dummyUSer2 ).getValue();
		userIds.add( dummyUSer2.getId() );

		System.out.println( "testGetMulitpleUsers: injected a new user with id " + dummyUSer2.getId() );

		User dummyUSer3 = createDummyAPIUser();
		dummyUSer3 = userService.createUser( dummyUSer3 ).getValue();
		userIds.add( dummyUSer3.getId() );

		System.out.println( "testGetMulitpleUsers: injected a new user with id " + dummyUSer3.getId() );

		String apiUrl = "/users";
		webClient.path( apiUrl );
		webClient.query( "id", userIds );


		List<com.projectx.sdk.user.impl.BasicUser> reponseUsers = null;

		try {

			System.out.println( "testGetMulitpleUsers: GET request to: " + webClient.getCurrentURI() );
			String reponseUsersString = webClient.get( String.class );
			ApiResponse<List<com.projectx.sdk.user.impl.BasicUser>> apiResponse = objectMapper.readValue(
					reponseUsersString,
					objectMapper.getTypeFactory().constructType( new TypeReference<ApiResponse<List<com.projectx.sdk.user.impl.BasicUser>>>(){} )
			);
			reponseUsers = apiResponse.getData();
			System.out.println( "testGetMulitpleUsers: " + apiUrl + " response: " + reponseUsersString );


		} catch( Exception e ) {

			e.printStackTrace();
			fail( e.getMessage() );
			return;

		}

		assertNotNull( reponseUsers );
		assertTrue( reponseUsers.size() == userIds.size() );
		for( com.projectx.sdk.user.User responseUser : reponseUsers ) {
			if( !userIds.contains( responseUser.getId() ) ) {
				fail( "response values do not match to what was request" );
			}
		}
	}

	/*
	@Test
	public void testDeleteUsers() {

	}
	*/

	private User createDummyAPIUser() {

		// create a dummy user object
		User user = new User();
		user.setId( (new Random()).nextInt() );
		user.setFirstName( "James" );
		user.setLastName( "Bond" );
		user.setPassword( "007" );
		user.setEmail( "james.bond@testing.com" );
		//user.setDateCreated( new Date() );

		return user;
	}

	private com.projectx.sdk.user.User createDummySDKUser() {

		// create a dummy user object
		com.projectx.sdk.user.User user = new com.projectx.sdk.user.impl.BasicUser();
		user.setId( (new Random()).nextInt(Integer.MAX_VALUE) );
		user.setFirstName( "James" );
		user.setLastName( "Bond" );
		user.setEmail( "james.bond@testing.com" );
		//user.setDateCreated( new Date() );

		return user;
	}

	private Form serializeToForm( com.projectx.sdk.user.User user ) {

		/*
		Map<String,String> userSerialized = (new ObjectMapper()).convertValue( user, HashMap.class );
		MultivaluedMap<String,String> formMap = new MultivaluedHashMap<String,String>( userSerialized );

		return new Form( formMap );
		*/

		Map<String,Object> map = (new ObjectMapper()).convertValue( user, HashMap.class );
		Form form = new Form();

		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				form.param(entry.getKey(), String.valueOf( entry.getValue() ));
			}
		}

		return form;

	}
}