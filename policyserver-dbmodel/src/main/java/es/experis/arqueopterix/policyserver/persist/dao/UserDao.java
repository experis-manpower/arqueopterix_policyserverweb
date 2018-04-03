package es.experis.arqueopterix.policyserver.persist.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.experis.arqueopterix.policyserver.persist.dbmodel.User;


@Repository
public class UserDao {

	private static final Logger LOGGER = LogManager.getLogger(UserDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Constructor
	 */
	public UserDao() {
		super();
	}

	public User findByUserName(String pUsername) {
		LOGGER.traceEntry();
		User user = null;
		try {
			Query query = entityManager.createQuery("SELECT u FROM User u where u.name = :username");
			query.setParameter("username", pUsername);
			user = (User) query.getSingleResult();
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			user = null;
		}
		LOGGER.traceExit();
		return user;
	}

	public List<User> findAll() {
		LOGGER.traceEntry();
		List<User> users = null;
		try {
			users = entityManager.createQuery("SELECT u FROM User u").getResultList();
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Users not found");
		}
		LOGGER.traceExit();
		return users;
	}

	public User find(int id) {
		LOGGER.traceEntry();
		User user = null;
		try {
			user = entityManager.find(User.class, id);
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Error getting user " + id);
		}
		LOGGER.traceExit();
		return user;
	}
	
	@Transactional
	public boolean addUser(User pUser)
	{
		LOGGER.debug("Call: addUser -- Params [pUser="+ pUser + "]");		
		try {
			entityManager.persist(pUser);			
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Error persisting user " + pUser.getUserName());
			LOGGER.debug("Return: addUser -- Params [result="+ false + "]");	
			return false;
		}
		LOGGER.debug("Return: addUser -- Params [result="+ true + "]");	
		return true;
	}

}