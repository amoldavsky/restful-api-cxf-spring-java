package com.projectx.logic.api.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
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

	@Transactional
	public Integer createUser( User user ) {

		Session session = sessionFactory.getCurrentSession();
		session.save( user );

		return user.getId();
	}

	@Transactional
	public User deleteUser( User user ) {

		Session session = sessionFactory.getCurrentSession();

		session.delete( user );

		return user;
	}

	public Integer updateUser( User user ) {

		Session session = sessionFactory.getCurrentSession();
		session.update( user );

		return user.getId();
	}


	public User getUser( int userId ) {

		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.createCriteria( User.class )
				.add( Restrictions.eq("id", userId) )
				.uniqueResult();
		return user;
	}

	public User getUserByEmail( String email ) {

		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.createCriteria( User.class )
				.add( Restrictions.eq( "email", email ) )
				.uniqueResult();

		return user;

	}

	public User getUserByUsername( String username ) {

		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.createCriteria( User.class )
				.add( Restrictions.eq( "username", username ) )
				.uniqueResult();

		return user;

	}

	public List<User> getUsers( Collection<Integer> userIds ) {

		Session session = sessionFactory.getCurrentSession();

		List<User> users = (List<User>) session.createCriteria( User.class )
				.add( Restrictions.in("id", userIds) )
				.list();

		return users;

	}

	public List<User> getUsers( Collection<Integer> userIds, int limit ) {

		Session session = sessionFactory.getCurrentSession();

		List<User> users = (List<User>) session.createCriteria( User.class )
				.add( Restrictions.in("id", userIds) )
				.setFirstResult(0).setMaxResults( limit )
				.list();

		return users;
	}

	public List<User> getAllUsers( int page, int rpp, int limit ) {

		Session session = sessionFactory.getCurrentSession();

		int skip = rpp * page;
		if( limit > 0 && skip >= limit ) {
			return Collections.<User>emptyList();
		}

		int count = rpp;
		// adjustments for the last page
		if( limit > 0 && skip + rpp > limit ) {
			count = limit - skip;
		}

		List<User> users = (List<User>) session.createCriteria( User.class )
				.setFirstResult( skip )
				.setMaxResults( count )
				.list();

		return users;
	}

}
