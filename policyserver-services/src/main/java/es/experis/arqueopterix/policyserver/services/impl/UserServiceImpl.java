package es.experis.arqueopterix.policyserver.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.experis.arqueopterix.policyserver.persist.dao.RoleDao;
import es.experis.arqueopterix.policyserver.persist.dao.UserDao;
import es.experis.arqueopterix.policyserver.persist.dbmodel.User;
import es.experis.arqueopterix.policyserver.services.UserService;

@Service("userserviceimpl")
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	

	@Override
	public User findByUserName(String username) {
		LOGGER.traceEntry();
		User user = userDao.findByUserName(username);
		LOGGER.traceExit(user);
		return user;
	}

	@Override
	public List<User> findAll() {
		LOGGER.traceEntry();
		List<User> result = new ArrayList<User>();
		result.addAll(userDao.findAll());
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public User find(int id) {
		LOGGER.traceEntry();
		User result = userDao.find(id);
		LOGGER.traceExit(result);
		return result;
	}

	@Override
	public boolean addUser(User pUser) {
		LOGGER.debug("Call: addUser -- Params [pUser=" + pUser +"]");
		boolean result = userDao.addUser(pUser);
		LOGGER.traceExit(result);
		LOGGER.debug("Return: addUser -- Params [result=" + result +"]");
		return result;
	}

	
	
}