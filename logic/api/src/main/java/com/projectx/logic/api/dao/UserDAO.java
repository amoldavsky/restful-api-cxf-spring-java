package com.projectx.logic.api.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.projectx.logic.api.data.User;

/**
 * The UserDAO
 * 
 * @author Assaf Moldavsky
 */
@Repository
@Transactional
public class UserDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	private User createDummyUser() {
		
		// create a dummy user object
		User user = new User();
		//user.setId( 007 );
		user.setFirstName( "James" );
		user.setLastName( "Bond" );
		user.setPassword( "007" );
		user.setEmail( "james.bond@testing.com" );
		user.setDateCreated( new Date() );
		
		return user;
	}
	
	@Transactional
    public Integer createUser( User user ) {
        
		User dummyUser = createDummyUser();
        
        return dummyUser.getId();
        
    }

    public User getUser( int userId ) {
        
    	User dummyUser = createDummyUser();
        dummyUser.setId( userId );
        
        return dummyUser;
        
    }
	
}
