package es.experis.arqueopterix.policyserver.services;

import java.util.List;

import es.experis.arqueopterix.policyserver.persist.dbmodel.User;

public interface UserService {
	List<User> findAll();
	User find(int id);
	User findByUserName(String username);
	boolean addUser(User pUser);
}