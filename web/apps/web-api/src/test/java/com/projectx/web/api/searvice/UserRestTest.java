package com.projectx.web.api.searvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.catalina.core.ApplicationContext;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.transport.local.LocalConduit;
import org.apache.cxf.jaxrs.client.WebClient;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.projectx.logic.api.service.UserService;
import com.projectx.logic.api.service.impl.UserServiceImpl;
import com.projectx.web.api.service.JaxrsService;
import com.projectx.web.api.service.UserRest;
import com.projectx.web.api.service.impl.UserRestImpl;
import com.projectx.web.api.test.config.spring.AppTestConfig;
import com.projectx.web.api.test.config.spring.ConfigTestUtils;
import com.projetx.sdk.user.User;
import com.projetx.sdk.user.impl.BasicUser;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.env.MockPropertySource;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebTestContextBootstrapper;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@ContextConfiguration( 
		loader = AnnotationConfigContextLoader.class,
		classes = { UserRestTest.class, AppTestConfig.class }
)
@PowerMockIgnore( {"javax.management.*"}) 
@WebAppConfiguration
//@BootstrapWith(WebTestContextBootstrapper.class)
public class UserRestTest extends BaseTest implements ApplicationContextAware {
	
	//@Autowired
	UserService userService;
	
	@Autowired
	Server jaxRsServer;
	
	@Autowired @Qualifier("ConfigUTesttils")
	ConfigTestUtils configUtil;
	
	UserRest userRestRS;
	
	ApplicationContext applicationContext;
	
	@Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }
	
	@Bean
	public ApplicationContext applicationContext() {
		return this.applicationContext;
	}
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		userService = Mockito.mock( UserService.class );
		userRestRS = Mockito.spy( new UserRestImpl() );
		ReflectionTestUtils.setField( userRestRS, "userService", userService );
		
		System.out.println( "assaf00:" + userService );
		
	}

	@After
    public void tearDown() throws Exception {

	}
	
	@Configuration
	@ComponentScan(
			basePackages = {
				"com.projectx.web.api.test",
				"com.projetx.web.api.service"
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
		Mockito.doReturn( null ).when( userService ).getUser( 11 );
		
		Integer testUserId = 1;
		
		//Server server = this.jaxRsServer;
		
		WebClient client = WebClient.create( "http://localhost:8080/api" );
		WebClient.getConfig(client).getRequestContext().put( LocalConduit.DIRECT_DISPATCH, Boolean.TRUE );
		client.accept( "application/json" );
		
		client.path( "/user/" + testUserId );
		
		User reponseUser = null;
		
		try {

			String response = client.get( String.class );
			reponseUser = (new ObjectMapper()).readValue( response, BasicUser.class );
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			
		}
		
		assertEquals( reponseUser.getId(), testUserId );
	}
	
	/**
	 * Tests the the /user/id endpoint for a negative id
	 */
	@Test
	public void testGetUserInvalidIdNegative() {
		Mockito.doReturn( null ).when( userService ).getUser( 11 );
		
		WebClient client = WebClient.create( "http://localhost:8080/api" );
		WebClient.getConfig(client).getRequestContext().put( LocalConduit.DIRECT_DISPATCH, Boolean.TRUE );
		client.accept( "application/json" );

		User reponseUser = null;
		
		// test for a negative int 
		Integer testUserId = -1;
		client.path( "/user/" + testUserId );
		
		try {

			String response = client.get( String.class );
			reponseUser = (new ObjectMapper()).readValue( response, BasicUser.class );
			
		} catch( Exception e ) {
			
			//e.printStackTrace();
			assertTrue( true );
			return;
			
		}
		
		fail( "was able to call /user/" + testUserId );
		
	}
	
	/**
	 * Tests the the /user/id endpoint for an alphanumeric id
	 */
	@Test
	public void testGetUserInvalidIdAlphanumeric() {
		
		WebClient client = WebClient.create( "http://localhost:8080/api" );
		WebClient.getConfig(client).getRequestContext().put( LocalConduit.DIRECT_DISPATCH, Boolean.TRUE );
		client.accept( "application/json" );
		
		User reponseUser = null;
		
		// test a latter in the id
		String badUserId = "123abc";
		client.path( "/user/" + badUserId );
		
		try {

			String response = client.get( String.class );
			reponseUser = (new ObjectMapper()).readValue( response, BasicUser.class );
			
		} catch( Exception e ) {
			
			//e.printStackTrace();
			assertTrue( true );
			return;
			
		}
		
		fail( "was able to call /user/" + badUserId );
		
	}
	
	/*
	@Test
	public void testCreateUser() {

	}
	
	@Test
	public void testUpdateUser() {

	}
	
	@Test
	public void testDeleteUser() {

	}
	
	@Test
	public void testGetUsers() {

	}
	
	@Test
	public void testDeleteUsers() {

	}
	*/
	
	private User createDummyUser() {
		
		// create a dummy user object
		User user = new com.projectx.logic.api.data.User();
		user.setId( (new Random()).nextInt() );
		user.setFirstName( "James" );
		user.setLastName( "Bond" );
		user.setPassword( "007" );
		user.setEmail( "james.bond@testing.com" );
		user.setDateCreated( new Date() );
		
		return user;
	}
}
