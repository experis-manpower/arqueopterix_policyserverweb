package es.experis.arqueopterix.policyserver.webcontroller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.experis.arqueopterix.policyserver.model.AlertJson;
import es.experis.arqueopterix.policyserver.model.AlertJsonFront;
import es.experis.arqueopterix.policyserver.model.SessionJson;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Alert;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Session;
import es.experis.arqueopterix.policyserver.services.SessionService;
import es.experis.arqueopterix.policyserver.util.IDGenerator;

@Controller
@RequestMapping("/sessions")
public class SessionController 
{
	
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
	
	@Autowired
	private SessionService sessionServ;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody List<SessionJson> usersList() {
        LOGGER.traceEntry();
        List<SessionJson> listJson = new ArrayList<>();
    	List<Session> list = sessionServ.findAll();
    	Iterator<Session> itera = list.iterator();
    	while (itera.hasNext()) {
			Session session = (Session) itera.next();
			
			// compruebo si hay una alarma asociada
			boolean anyAlarm = sessionServ.findAlarmsAssociated(session.getIdsession());
			
			
//			SessionJson sessJson = new SessionJson(""+	session.getIdsession(), session.getOrigin(), 
//					session.getDestination(), session.getOriginPort(), session.getDestinationPort());
			SessionJson sessJson = new SessionJson(""+	session.getIdsession(), session.getOrigin(), 
					session.getDestination(), session.getOriginPort(), session.getDestinationPort(), String.valueOf(anyAlarm));
			listJson.add(sessJson);
		}    	
    	
        LOGGER.traceExit(list);
        return listJson;
    }
	
	@RequestMapping(value = "/listbycompany/{id}", method = RequestMethod.GET)
    public @ResponseBody List<SessionJson> sessionList(@PathVariable("id") int id) {
        LOGGER.traceEntry();
        List<SessionJson> listJson = new ArrayList<>();
    	List<Session> list = sessionServ.findByCompanyId(id);
    	Iterator<Session> itera = list.iterator();
    	while (itera.hasNext()) {
			Session session = (Session) itera.next();
			
			// compruebo si hay una alarma asociada
			boolean anyAlarm = sessionServ.findAlarmsAssociated(session.getIdsession());
			
			SessionJson sessJson = new SessionJson(""+	session.getIdsession(), session.getOrigin(), 
					session.getDestination(), session.getOriginPort(), session.getDestinationPort(), String.valueOf(anyAlarm));
			listJson.add(sessJson);
		}    	
    	
        LOGGER.traceExit(list);
        return listJson;
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<SessionJson> addSession(@RequestBody  SessionJson pSession) {
        LOGGER.traceEntry();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //Genero un ID de sesión
        pSession.setSessionID(""+IDGenerator.generateRandom());
        
        LOGGER.info("Message from "+auth.getName());
        LOGGER.info(" "+pSession.toString());        
        
        //Convierto a Entity el objeto
        Session sess = pSession.convertToSessionEntity();
        //Save in DB.        
        sessionServ.addSession(sess, auth.getName());        
        
        //ON ERROR
        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        LOGGER.traceExit();        
        return new ResponseEntity<SessionJson>(pSession,HttpStatus.OK);
    }
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<SessionJson> removeSession(@RequestBody SessionJson pSession) {
        LOGGER.traceEntry();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      
        LOGGER.info("Message from "+auth.getName());
        LOGGER.info(" "+pSession.toString());
        //Convierto a Entity el objeto
        Session sess = pSession.convertToSessionEntity();
        //Remove from DB.
        sessionServ.removeSession(sess);
        
        //ON ERROR
        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        LOGGER.traceExit();        
        return new ResponseEntity<SessionJson>(pSession,HttpStatus.OK);
    }
	
	@RequestMapping(value = "/alert", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<AlertJson> addAlert(@RequestBody  AlertJson pAlert) {
        LOGGER.traceEntry();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      
        LOGGER.info("Message from "+auth.getName());
        LOGGER.info(" "+pAlert.toString());        
        //Convierto a Entity el objeto
        Alert alert = pAlert.convertToAlertEntity();
        //Convierto Canal de Medidas como objeto Session
        Session sess =  pAlert.getMeasurementChannel().convertToSessionEntity();
        //Save in DB. 
        sessionServ.addAlert(alert, Integer.parseInt(pAlert.getSession().getSessionID()), sess);
        //ON ERROR
        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        LOGGER.traceExit();        
        return new ResponseEntity<AlertJson>(pAlert,HttpStatus.OK);
    }
	
	@RequestMapping(value = "/alertlist", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<AlertJsonFront>> getAlertList() {
        LOGGER.traceEntry();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("Message from "+auth.getName());       
        List<Alert> alertList = sessionServ.getAlertList(); 
        List<AlertJsonFront> alertJsonFrontList = new ArrayList<AlertJsonFront>();
        
        for(Alert alert: alertList) {        	
        	AlertJsonFront ajf = new AlertJsonFront();
        	ajf.convertToAlertJsonFront(alert);
        	alertJsonFrontList.add(ajf);
        }
        
        LOGGER.traceExit();        
        return new ResponseEntity<List<AlertJsonFront>>(alertJsonFrontList,HttpStatus.OK);
    }
	
	@RequestMapping(value = "/alertlist/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody ResponseEntity<List<AlertJsonFront>> getAlertListById(@PathVariable("id") int id) {
        LOGGER.traceEntry();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("Message from "+auth.getName());
        
        List<Alert> alertList = sessionServ.getAlertListById(id); 
		List<AlertJsonFront> alertJsonFrontList = new ArrayList<AlertJsonFront>();
		        
		for(Alert alert: alertList) {        	
			AlertJsonFront ajf = new AlertJsonFront();
		    ajf.convertToAlertJsonFront(alert);
		    alertJsonFrontList.add(ajf);
		}        
        
        LOGGER.traceExit();        
        return new ResponseEntity<List<AlertJsonFront>>(alertJsonFrontList,HttpStatus.OK);
    }

	
	
}
