package es.experis.arqueopterix.policyserver.initializer;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import es.experis.arqueopterix.config.AppConfig;
import es.experis.arqueopterix.config.MvcConfig;
import es.experis.arqueopterix.config.RestSecurityConfig;
import es.experis.arqueopterix.policyserver.config.JPAConfig;
import es.experis.arqueopterix.policyserver.config.ServiceConfig;

public class MvcWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class, RestSecurityConfig.class, ServiceConfig.class, JPAConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { MvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
