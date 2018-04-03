package es.experis.arqueopterix.policyserver.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.experis.arqueopterix.policyserver.persist.dao.PolicyDao;
import es.experis.arqueopterix.policyserver.persist.dao.SessionDao;
import es.experis.arqueopterix.policyserver.persist.dao.UserDao;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Alert;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Policies;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Session;
import es.experis.arqueopterix.policyserver.persist.dbmodel.User;
import es.experis.arqueopterix.policyserver.services.PolicyService;
import es.experis.arqueopterix.policyserver.services.SessionService;

@Service
public class PolicyServiceImpl implements PolicyService {

	private static final Logger LOGGER = LogManager.getLogger(SessionService.class);

	@Autowired
	private PolicyDao policyDao;

	@Autowired
	private SessionDao sessionDao;
	
	@Autowired
	private UserDao userDao;

	@Override
	public List<Policies> findAll() {
		LOGGER.traceEntry();
		List<Policies> result = new ArrayList<Policies>();
		result.addAll(policyDao.findAll());
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public Policies find(int id) {
		LOGGER.traceEntry();
		Policies result = policyDao.find(id);
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public Policies addPolicy(Policies pPolicies, String pUsername) {
		LOGGER.traceEntry();
		//Inserto la referencia del usuario
		User mUser = userDao.findByUserName(pUsername);
		pPolicies.setUser(mUser);
		Policies result = policyDao.addPolicies(pPolicies);
		LOGGER.traceExit(result);
		return result;
	}
	
	@Override
	public Policies addPolicyById(Policies pPolicies, int id) {
		LOGGER.traceEntry();
		//Inserto la referencia del usuario
		User mUser = userDao.find(id);
		pPolicies.setUser(mUser);
		Policies result = policyDao.addPolicies(pPolicies);
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public boolean removePolicy(Policies pPolicies) {
		LOGGER.traceEntry();
		
		
		boolean result = policyDao.removePolicies(pPolicies);
		LOGGER.traceExit(result);
		return result;
	}

	

	public Alert checkSLA(Alert pAlert, Session measurementChannel)
	{
		
		//TODO MANAGE ALERT, FIRE SOME POLICY
		List<Session> sessions = sessionDao.getActiveSessions(pAlert.getSession().getDestination());
		LOGGER.info("Sessiones Activas Matching EndPoint "+pAlert.getSession().getDestination()+" = "+sessions.size());
		
		//Get Policies
		List<Policies> listPolicies = policyDao.getPoliciesByParam(pAlert.getSession().getUserid().getId(), pAlert.getSession().getDestination());
		if(listPolicies != null & listPolicies.size()>0)
		{
			LOGGER.info("Encontradas "+listPolicies.size()+" POLICIES to check SLA");
			//Obtengo el número de sesiones, no deberíamos añadir 2 políticas diferentes para el mismo EndPoint (Se restringirá luego por puerto)
			Policies activePolicy = listPolicies.get(0);
			int maxSessions = activePolicy.getNumSessions();
			boolean sessionFound = false;
			Iterator<Session> itera = sessions.iterator();
			while (itera.hasNext() && maxSessions>0) {
				Session session = (Session) itera.next();
				if(session.getIdsession() == pAlert.getSession().getIdsession())
				{
					sessionFound = true;
					break;
				}
				maxSessions--;				
			}
			
			//La sesión es válida en el contexto del SLA
			if(sessionFound)
			{
				//Verificamos que está dentro del SLA por política o no.
				if(pAlert.getBandwidthup() < activePolicy.getBandwidthUP() || pAlert.getBandwidthdown() < activePolicy.getBandwidthDOWN() 
						|| pAlert.getLatency() > activePolicy.getPingMaxDelay())
				{
					LOGGER.info("SLA FAILED");
					//LLAMO AL CONTROLADOR SDN SESION PRINCIPAL y MEASUREMENT SESSION
					pAlert.setFireSLA(true);					
				}
				else
				{
					LOGGER.info("SLA CORRECT");
					pAlert.setFireSLA(false);
				}			
				pAlert.setPolicy(activePolicy);
			}
			else
			{
				LOGGER.info("Sessions exceeded max SLA permited");
			}
			
		}
		else
		{
			LOGGER.info("NO POLICY FOUND TO ENDPOINT: "+pAlert.getSession().getDestination()+" FOR USER "+pAlert.getSession().getUserid().getUserName());
			//sessionDao.removeSession(pAlert.getSession());
		}
		
		return pAlert;
	}

	@Override
	public List<Policies>  findByCompany(int id) {
		LOGGER.traceEntry();
		List<Policies> result = policyDao.findByCompany(id);
		LOGGER.traceExit(result);
		return result;
	}
	
}