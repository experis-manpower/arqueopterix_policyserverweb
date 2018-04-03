package es.experis.arqueopterix.policyserver.services;

import java.util.List;

import es.experis.arqueopterix.policyserver.persist.dbmodel.Alert;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Policies;
import es.experis.arqueopterix.policyserver.persist.dbmodel.Session;

public interface PolicyService {
	List<Policies> findAll();
	Policies find(int id);
	Policies addPolicy(Policies pPolicy, String usernam);
	boolean removePolicy(Policies pPolicy);
	public Alert checkSLA(Alert pAlert, Session measurementChannel);
	List<Policies>  findByCompany(int id);
	Policies addPolicyById(Policies pPolicies, int id);
}