package es.experis.arqueopterix.policyserver.persist.dbmodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the session database table.
 * 
 */
@Entity
@NamedQuery(name="Policies.findAll", query="SELECT s FROM Policies s")
public class Policies implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idPolicy;

	private String endPoint;

	private int numSessions;

	private float bandwidthUP;

	private float bandwidthDOWN;
	
	private float pingMaxDelay;
	
	//bi-directional many-to-one association to Session
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;
	
	@OneToMany(mappedBy="policy")
	private List<Alert> alerts;
	
	public List<Alert> getAlerts() {
		return alerts;
	}


	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Policies() {
	}


	public int getIdPolicy() {
		return idPolicy;
	}


	public void setIdPolicy(int idPolicy) {
		this.idPolicy = idPolicy;
	}


	public String getEndPoint() {
		return endPoint;
	}


	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}


	public int getNumSessions() {
		return numSessions;
	}


	public void setNumSessions(int numSessions) {
		this.numSessions = numSessions;
	}


	public float getBandwidthUP() {
		return bandwidthUP;
	}


	public void setBandwidthUP(float bandwidthUP) {
		this.bandwidthUP = bandwidthUP;
	}


	public float getBandwidthDOWN() {
		return bandwidthDOWN;
	}


	public void setBandwidthDOWN(float bandwidthDOWN) {
		this.bandwidthDOWN = bandwidthDOWN;
	}


	public float getPingMaxDelay() {
		return pingMaxDelay;
	}


	public void setPingMaxDelay(float pingMaxDelay) {
		this.pingMaxDelay = pingMaxDelay;
	}




	

}