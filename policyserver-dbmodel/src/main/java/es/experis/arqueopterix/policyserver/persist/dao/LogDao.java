package es.experis.arqueopterix.policyserver.persist.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.experis.arqueopterix.policyserver.persist.dbmodel.Log;

@Repository
public class LogDao {

	private static final Logger LOGGER = LogManager.getLogger(LogDao.class);

	@PersistenceContext
	private EntityManager entityManager;

	public LogDao() {
		super();
	}

	public List<Log> findAll() {
		LOGGER.traceEntry();
		List<Log> logs = null;
		try {
			logs = entityManager.createQuery("SELECT l FROM Log l").getResultList();
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Floors not found");
		}
		LOGGER.traceExit();
		return logs;
	}

	@Transactional
	public boolean addCustomLog(String alertType, String description) {
		LOGGER.traceEntry();
		try {
			Date utilDate = new Date();		
			
			Log log = new Log();
			log.setAlertType(alertType);
			log.setDescription(description);		
			log.setDateTime(utilDate.toString());
			
			entityManager.persist(log);
			LOGGER.info("--Evento: " + log.getAlertType() + ", ha sido guardado--");		
			LOGGER.traceExit();
			return true;
		} catch (IllegalArgumentException | NoResultException | JpaSystemException ex) {
			LOGGER.error("Floors not found");
			LOGGER.traceExit();
			return false;
		}
	}

}
