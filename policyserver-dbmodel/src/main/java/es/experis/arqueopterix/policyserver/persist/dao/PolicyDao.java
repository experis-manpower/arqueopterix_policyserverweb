package es.experis.arqueopterix.policyserver.persist.dao;

import java.util.ArrayList;
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

import es.experis.arqueopterix.policyserver.persist.dbmodel.Policies;

@Repository
public class PolicyDao 
{

	private static final Logger LOGGER = LogManager.getLogger(PolicyDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Constructor
	 */
	public PolicyDao() {
		super();
	}

	public List<Policies> findAll() {
		LOGGER.traceEntry();
		List<Policies> Policiess = null;
		try {
			Policiess = entityManager.createQuery("SELECT u FROM Policies u").getResultList();
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Policiess not found");
		}
		LOGGER.traceExit();
		return Policiess;
	}

	public Policies find(int id) {
		LOGGER.traceEntry();
		Policies Policies = null;
		try {
			Policies = entityManager.find(Policies.class, id);
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Error getting Policies " + id);
		}
		LOGGER.traceExit();
		return Policies;
	}
	
	@Transactional
	public Policies addPolicies(Policies pPolicies)
	{
		LOGGER.traceEntry();		
		try {			
			entityManager.persist(pPolicies);			
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Error persisting Policies " + pPolicies.getIdPolicy());
		}
		LOGGER.traceExit();
		return pPolicies;
	}
	
	@Transactional
	public boolean removePolicies(Policies pPolicies)
	{
		LOGGER.traceEntry();		
		try {
			pPolicies = this.find(pPolicies.getIdPolicy());
			entityManager.remove(pPolicies);			
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Error removing Policies " + pPolicies.getIdPolicy());
			return false;
		}
		LOGGER.traceExit();
		return true;
	}

	public List<Policies> getPoliciesByParam(int id, String destination) {
		LOGGER.traceEntry();
		
		List<Policies> listPolicies = null;
		try {
			Query que = entityManager.createQuery("SELECT u FROM Policies u where u.user.id = :userid and u.endPoint = :endpoint");
			que.setParameter("endpoint", destination);
			que.setParameter("userid", id);
			listPolicies = (List<Policies>)que.getResultList();
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Policies not found");
		}
		LOGGER.traceExit();
		return listPolicies;
	}

	public List<Policies> findByCompany(int id) {
		LOGGER.traceEntry();
		
		List<Policies> listPolicies = null;
		try {
			Query que = entityManager.createQuery("SELECT u FROM Policies u where u.user.id = :userid");
			que.setParameter("userid", id);
			listPolicies = (List<Policies>)que.getResultList();
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Policies not found");
		}
		LOGGER.traceExit();
		return listPolicies;
	}
	


}
