package es.experis.arqueopterix.policyserver.services;

import java.util.List;

import es.experis.arqueopterix.policyserver.persist.dbmodel.Alert;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Session;

public interface SessionService {
	List<Session> findAll();
	Session find(int id);
	Session addSession(Session pSession, String usernam);
	Alert addAlert(Alert pAlert, int pSession, Session pMesasurementChannel);
	boolean removeSession(Session pSession);
	List<Alert> getAlertList();
	boolean findAlarmsAssociated(int idsession);
	List<Alert> getAlertListById(int id);
	List<Session> findByCompanyId(int id);
	
}