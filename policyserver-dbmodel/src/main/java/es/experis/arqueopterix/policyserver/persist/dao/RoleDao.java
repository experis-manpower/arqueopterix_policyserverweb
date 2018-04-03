package es.experis.arqueopterix.policyserver.persist.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class RoleDao {

	private static final Logger LOGGER = LogManager.getLogger(RoleDao.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Constructor
	 */
	public RoleDao() {
		super();
	}
	
	

}