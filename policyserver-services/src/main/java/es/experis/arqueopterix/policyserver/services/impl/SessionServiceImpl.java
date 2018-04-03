package es.experis.arqueopterix.policyserver.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.experis.arqueopterix.policyserver.persist.dao.SessionDao;
import es.experis.arqueopterix.policyserver.persist.dao.UserDao;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Alert;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Session;
import es.experis.arqueopterix.policyserver.persist.dbmodel.User;
import es.experis.arqueopterix.policyserver.services.SessionService;

@Service
public class SessionServiceImpl implements SessionService {

	private static final Logger LOGGER = LogManager.getLogger(SessionService.class);

	@Autowired
	private SessionDao sessionDao;
	
	@Autowired
	private PolicyServiceImpl policyService;

	@Autowired
	private UserDao userDao;

	@Override
	public List<Session> findAll() {
		LOGGER.traceEntry();
		List<Session> result = new ArrayList<Session>();
		result.addAll(sessionDao.findAll());
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public Session find(int id) {
		LOGGER.traceEntry();
		Session result = sessionDao.find(id);
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public Session addSession(Session pSession, String pUsername) {
		LOGGER.traceEntry();
		//Inserto la referencia del usuario
		User mUser = userDao.findByUserName(pUsername);
		pSession.setUserid(mUser);
		//Establezco fecha creación
		Date mDate = new Date();
		pSession.setCreationDate(mDate);
		pSession.setExpired(false);
		Session result = sessionDao.addSession(pSession);
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public boolean removeSession(Session pSession) {
		LOGGER.traceEntry();
		pSession = sessionDao.find(pSession.getIdsession());
		
		boolean result = sessionDao.removeSession(pSession);
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public Alert addAlert(Alert pAlert, int pSession, Session measurementChannel) {
		LOGGER.traceEntry();
		//Inserto la referencia de la session
		Session mSession = sessionDao.find(pSession);
		pAlert.setSession(mSession);
		//Establezco fecha recepción
		Date mDate = new Date();
		pAlert.setReceptionDate(mDate);
		
		//MANAGE ALERT, FIRE SOME POLICY
		pAlert = policyService.checkSLA(pAlert, measurementChannel);
		
		Alert result = sessionDao.addAlert(pAlert);
		LOGGER.traceExit(result);
		return result;
	}
	
	public List<Alert> getAlertList() {
		LOGGER.traceEntry();		
		List<Alert> alertList = sessionDao.getAlertList();
		LOGGER.traceExit(alertList);
		return alertList;
	}

	@Override
	public boolean findAlarmsAssociated(int idsession) {
		LOGGER.traceEntry();
		boolean result = false;		
		result = sessionDao.findAlarmBySession(idsession);		
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public List<Alert> getAlertListById(int id) {
		LOGGER.traceEntry();
		List<Alert> alertList = sessionDao.getAlertListById(id);
		LOGGER.traceExit(alertList);
		return alertList;
	}

	@Override
	public List<Session> findByCompanyId(int id) {
		LOGGER.traceEntry();
		List<Session> result = new ArrayList<Session>();
		result.addAll(sessionDao.findbyCompanyId(id));
		LOGGER.traceExit(result);
		return result;
	}

	
	
}