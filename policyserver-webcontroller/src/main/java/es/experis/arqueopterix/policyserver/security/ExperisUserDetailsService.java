package es.experis.arqueopterix.policyserver.security;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.experis.arqueopterix.policyserver.persist.dao.UserDao;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Role;
import es.experis.arqueopterix.policyserver.persist.dbmodel.User;


/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class ExperisUserDetailsService implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(ExperisUserDetailsService.class);

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
//		return null;
//	}

    @Autowired
    private UserDao userDao;

    @Transactional
    public UserDetails loadUserByUsername(final String name) {
    	LOGGER.debug("Authenticating {}", name);

        User user = userDao.findByUserName(name);
        if (user == null) {
            throw new UsernameNotFoundException("User " + name + " was not found in the database");
        } else if (!user.getEnabled()) {
            throw new UserNotEnabledException("User " + name + " was not enabled");
        }

        Collection<GrantedAuthority> grantedRoles = new ArrayList<GrantedAuthority>();
        for (Role role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
            grantedRoles.add(grantedAuthority);
        }
        org.springframework.security.core.userdetails.User localUser = new org.springframework.security.core.userdetails.User(name, user.getPassword(), grantedRoles);

        return localUser;
    }
}