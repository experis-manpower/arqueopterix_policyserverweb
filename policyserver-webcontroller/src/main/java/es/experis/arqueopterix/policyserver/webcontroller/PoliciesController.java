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

import es.experis.arqueopterix.policyserver.model.PolicyJson;
import es.experis.arqueopterix.policyserver.model.PolicyJsonFront;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Policies;
import es.experis.arqueopterix.policyserver.persist.dbmodel.User;
import es.experis.arqueopterix.policyserver.services.PolicyService;
import es.experis.arqueopterix.policyserver.services.UserService;
import es.experis.arqueopterix.policyserver.util.IDGenerator;


@Controller
@RequestMapping("/policies")
public class PoliciesController {
	
	private static final Logger LOGGER = LogManager.getLogger(UserController.class);
	
	@Autowired
	private PolicyService policyService;
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody List<PolicyJsonFront> policiesList() {
        LOGGER.traceEntry();
        List<PolicyJsonFront> listJson = new ArrayList<>();
    	List<Policies> list = policyService.findAll();
    	Iterator<Policies> itera = list.iterator();
    	while (itera.hasNext()) {
			Policies policies = (Policies) itera.next();
			PolicyJsonFront sessJson = new PolicyJsonFront(policies.getEndPoint(), ""+policies.getNumSessions(), 
					""+policies.getBandwidthUP(), ""+policies.getBandwidthDOWN(), ""+policies.getPingMaxDelay(), ""+policies.getIdPolicy());
			User user = policies.getUser();
			int id = user.getId();
			sessJson.setPolicyUser(id);
			listJson.add(sessJson);
		}    	
    	
        LOGGER.traceExit(list);
        return listJson;
    }
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<PolicyJson> addPolicy(@RequestBody  PolicyJson pPolicy) {
        LOGGER.traceEntry();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        //Genero un ID de sesión
        pPolicy.setPolicyID(""+IDGenerator.generateRandom());
        
        LOGGER.info("Message from "+auth.getName());
        LOGGER.info(" "+pPolicy.toString());        
        
        //Convierto a Entity el objeto
        Policies sess = pPolicy.convertToPolicyEntity();
        //Save in DB.        
        policyService.addPolicy(sess, auth.getName()); 
        
        //ON ERROR
        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        LOGGER.traceExit();        
        return new ResponseEntity<PolicyJson>(pPolicy,HttpStatus.OK);
    }
	
	
	@RequestMapping(value = "/add/{id}", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<PolicyJson> addPolicyByCompany(@PathVariable("id") int id, @RequestBody  PolicyJson pPolicy) {
        LOGGER.traceEntry();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        //Genero un ID de sesión
        pPolicy.setPolicyID(""+IDGenerator.generateRandom());
        
        LOGGER.info("Message from "+auth.getName());
        LOGGER.info(" "+pPolicy.toString());        
                
        //Convierto a Entity el objeto
        Policies sess = pPolicy.convertToPolicyEntity();
        //Save in DB.        
        policyService.addPolicyById(sess, id); 
        
        //ON ERROR
        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        LOGGER.traceExit();        
        return new ResponseEntity<PolicyJson>(pPolicy,HttpStatus.OK);
    }

	

	@RequestMapping(value = "/remove", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<PolicyJson> removePolicy(@RequestBody PolicyJson pPolicy) {
        LOGGER.traceEntry();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                
        LOGGER.info("Message from "+auth.getName());
        LOGGER.info(" "+pPolicy.toString());
        //Convierto a Entity el objeto
        Policies policy = pPolicy.convertToPolicyEntity();
        //Remove from DB.
        policyService.removePolicy(policy);
        //ON ERROR
        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        LOGGER.traceExit();    
        return null;
    }
	
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<PolicyJson> getPolicy(@PathVariable("id") int id) {
        LOGGER.traceEntry();
    	//Search Policy from DB
        Policies policies = policyService.find(id);
        PolicyJson policy = new PolicyJson(policies.getEndPoint(), ""+policies.getNumSessions(), 
				""+policies.getBandwidthUP(), ""+policies.getBandwidthDOWN(), ""+policies.getPingMaxDelay(), ""+policies.getIdPolicy());
        LOGGER.traceExit();
        return new ResponseEntity<PolicyJson>(policy,HttpStatus.OK);
        
    }
	
	@RequestMapping(value = "/listbycompany/{id}", method = RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<PolicyJson>> getPolicyByCompany(@PathVariable("id") int id) {
        LOGGER.traceEntry();
    	//Search Policy from DB
        List<Policies> policies = policyService.findByCompany(id);
        List<PolicyJson> policiesJson = new ArrayList<PolicyJson>();
        
        for(Policies policie: policies) {
        	PolicyJson policy = new PolicyJson(policie.getEndPoint(), ""+policie.getNumSessions(), 
    				""+policie.getBandwidthUP(), ""+policie.getBandwidthDOWN(), ""+policie.getPingMaxDelay(), ""+policie.getIdPolicy());
        	policiesJson.add(policy);
        }
        
        
        LOGGER.traceExit();
        return new ResponseEntity<List<PolicyJson>>(policiesJson,HttpStatus.OK);
        
    }
	
}
