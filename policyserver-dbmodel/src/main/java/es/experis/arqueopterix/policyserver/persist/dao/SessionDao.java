package es.experis.arqueopterix.policyserver.persist.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import es.experis.arqueopterix.policyserver.persist.dbmodel.Alert;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Session;
import es.experis.arqueopterix.policyserver.persist.dbmodel.User;

@Repository
public class SessionDao 
{

	private static final Logger LOGGER = LogManager.getLogger(SessionDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Constructor
	 */
	public SessionDao() {
		super();
	}

	public List<Session> findAll() {
		LOGGER.traceEntry();
		List<Session> sessions = null;
		try {
			sessions = entityManager.createQuery("SELECT u FROM Session u where u.expired = 0").getResultList();
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Sessions not found");
		}
		LOGGER.traceExit();
		return sessions;
	}

	public Session find(int id) {
		LOGGER.traceEntry();
		Session session = null;
		try {
			session = entityManager.find(Session.class, id);
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Error getting session " + id);
		}
		LOGGER.traceExit();
		return session;
	}
	
	@Transactional
	public Session addSession(Session pSession)
	{
		LOGGER.traceEntry();		
		try {			
			entityManager.persist(pSession);			
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Error persisting session " + pSession.getIdsession());
		}
		LOGGER.traceExit();
		return pSession;
	}
	
	@Transactional
	public boolean removeSession(Session pSession)
	{
		LOGGER.traceEntry();		
		try {
			pSession.setExpired(true);
			entityManager.merge(pSession);			
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Error removing session " + pSession.getIdsession());
			return false;
		}
		LOGGER.traceExit();
		return true;
	}
	
	
	@Transactional
	public Alert addAlert(Alert pAlert)
	{
		LOGGER.traceEntry();		
		try {			
			entityManager.persist(pAlert);			
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Error persisting alert " + pAlert.getIdalert());
		}
		LOGGER.traceExit();
		return pAlert;
	}

	public List<Session> getActiveSessions(String pEndPoint) {
		LOGGER.traceEntry();
		
		List<Session> sessionsList = null;
		try {
			Query que = entityManager.createQuery("SELECT u FROM Session u where u.destination = :destination and u.expired = 0 order by u.creationDate desc");
			que.setParameter("destination", pEndPoint);
			sessionsList = (List<Session>)que.getResultList();
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Sessions not found");
		}
		LOGGER.traceExit();
		return sessionsList;
		
	}
	
	
	public List<Alert> getAlertList()
	{
		LOGGER.traceEntry();
		List<Alert> alerts = null;
		try {
			alerts = entityManager.createQuery("SELECT u FROM Alert u").getResultList();
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Alerts not found");
		}
		LOGGER.traceExit();
		return alerts;
	}

	public boolean findAlarmBySession(int idsession) {
		LOGGER.traceEntry();
		boolean result = false;
		List<Alert> alerts = null;
		try {
			
			Query que = entityManager.createQuery("SELECT u FROM Alert u where u.session.idsession = :idsession");
			que.setParameter("idsession", idsession);
			alerts = (List<Alert>)que.getResultList();
			if(alerts.size() > 0) {
				result = true;
			}
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Alerts not found");
		}
		LOGGER.traceExit();
		return result;
	}

	public List<Alert> getAlertListById(int id) {
		LOGGER.traceEntry();
		List<Alert> alerts = new ArrayList<Alert>();
		try {			
			Query que = entityManager.createQuery("SELECT u FROM Alert u where u.session.idsession = :idsession");
			que.setParameter("idsession", id);
			alerts = (List<Alert>)que.getResultList();			
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Alerts not found");
		}
		LOGGER.traceExit();
		return alerts;
	}

	public Collection<? extends Session> findbyCompanyId(int id) {
		LOGGER.traceEntry();
		
		List<Session> sessionsList = new ArrayList<Session>();;
		try {
			Query que = entityManager.createQuery("SELECT u FROM Session u where u.userid.id = :userid");
			que.setParameter("userid", id);
			sessionsList = (List<Session>)que.getResultList();
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Sessions not found");
		}
		LOGGER.traceExit();
		return sessionsList;
	}


}
