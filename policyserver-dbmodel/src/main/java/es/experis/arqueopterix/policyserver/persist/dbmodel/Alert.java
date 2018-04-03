package es.experis.arqueopterix.policyserver.persist.dbmodel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the alert database table.
 * 
 */
@Entity
@NamedQuery(name="Alert.findAll", query="SELECT a FROM Alert a")
public class Alert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idalert;

	private float bandwidthdown;

	private float bandwidthup;

	private float jitter;

	private float latency;

	private float packetloss;

	private String qoslevel;
	
	private Date receptionDate;

	//bi-directional many-to-one association to Session
	@ManyToOne
	@JoinColumn(name="sessionid")
	@JsonIgnore
	private Session session;
	
	//bi-directional many-to-one association to Session
	@ManyToOne
	@JoinColumn(name="policyID")
	@JsonIgnore
	private Policies policy;
	
	private boolean fireSLA;

	public Alert() {
	}

	public int getIdalert() {
		return this.idalert;
	}

	public void setIdalert(int idalert) {
		this.idalert = idalert;
	}

	public float getBandwidthdown() {
		return this.bandwidthdown;
	}

	public void setBandwidthdown(float bandwidthdown) {
		this.bandwidthdown = bandwidthdown;
	}

	public float getBandwidthup() {
		return this.bandwidthup;
	}

	public void setBandwidthup(float bandwidthup) {
		this.bandwidthup = bandwidthup;
	}

	public float getJitter() {
		return this.jitter;
	}

	public void setJitter(float jitter) {
		this.jitter = jitter;
	}

	public float getLatency() {
		return this.latency;
	}

	public void setLatency(float latency) {
		this.latency = latency;
	}

	public float getPacketloss() {
		return this.packetloss;
	}

	public void setPacketloss(float packetloss) {
		this.packetloss = packetloss;
	}

	public String getQoslevel() {
		return this.qoslevel;
	}

	public void setQoslevel(String qoslevel) {
		this.qoslevel = qoslevel;
	}

	public Session getSession() {
		return this.session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Date getReceptionDate() {
		return receptionDate;
	}

	public void setReceptionDate(Date receptionDate) {
		this.receptionDate = receptionDate;
	}

	public boolean isFireSLA() {
		return fireSLA;
	}

	public void setFireSLA(boolean fireSLA) {
		this.fireSLA = fireSLA;
	}

	public Policies getPolicy() {
		return policy;
	}

	public void setPolicy(Policies policy) {
		this.policy = policy;
	}
	
}