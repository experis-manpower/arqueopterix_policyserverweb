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
@NamedQuery(name="Session.findAll", query="SELECT s FROM Session s")
public class Session implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idsession;

	private String destination;

	private String destinationPort;

	private String origin;

	private String originPort;
	
	private Date creationDate;
	
	private boolean expired;

	//bi-directional many-to-one association to Session
	@ManyToOne
	@JoinColumn(name="userid")
	private User userid;
	
	//bi-directional many-to-one association to Alert
	@OneToMany(mappedBy="session")
	private List<Alert> alerts;

	public Session() {
	}

	public int getIdsession() {
		return this.idsession;
	}

	public void setIdsession(int idsession) {
		this.idsession = idsession;
	}

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationPort() {
		return this.destinationPort;
	}

	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}

	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getOriginPort() {
		return this.originPort;
	}

	public void setOriginPort(String originPort) {
		this.originPort = originPort;
	}
	
	public User getUserid() {
		return userid;
	}

	public void setUserid(User userid) {
		this.userid = userid;
	}

	public List<Alert> getAlerts() {
		return this.alerts;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}

	public Alert addAlert(Alert alert) {
		getAlerts().add(alert);
		alert.setSession(this);

		return alert;
	}

	public Alert removeAlert(Alert alert) {
		getAlerts().remove(alert);
		alert.setSession(null);

		return alert;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

}