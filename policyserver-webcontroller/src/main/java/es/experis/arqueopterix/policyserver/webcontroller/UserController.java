package es.experis.arqueopterix.policyserver.webcontroller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.experis.arqueopterix.policyserver.model.SimpleUser;
import es.experis.arqueopterix.policyserver.persist.dbmodel.User;
import es.experis.arqueopterix.policyserver.services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	private static final Logger LOGGER = LogManager.getLogger(UserController.class);

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	@Qualifier("userserviceimpl")
	private UserService service;

	public UserController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<User> usersList() {
		LOGGER.debug("Call:  usersList() ");
		List<User> list = service.findAll();
		LOGGER.debug("Return:  usersList() -- Params [list=" + list + "]");		
		return list;
	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") int id) {
		LOGGER.traceEntry();
		User user = service.find(id);
		if (user == null) {
			LOGGER.traceExit("No existe usuario con id " + id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		LOGGER.traceExit(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/sign-up", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> signUp(@RequestBody SimpleUser simpleUser) {
		LOGGER.debug("Call:  signUp() -- Params [simpleUser=" + simpleUser + "]");
		simpleUser.setPassword(bCryptPasswordEncoder.encode(simpleUser.getPassword()));
		User user = new User();
		user.setName(simpleUser.getUsername());
		user.setUserName(simpleUser.getUsername());
		user.setPassword(simpleUser.getPassword());
		user.setCompany(simpleUser.getCompany());
		user.setEnabled(true);	
		boolean result = service.addUser(user);
		LOGGER.debug("Return:  getUserAccount() -- Params [result=" + result + "]");		
		if(result) {			
			return new ResponseEntity<>(HttpStatus.OK);						
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}		
	}

}