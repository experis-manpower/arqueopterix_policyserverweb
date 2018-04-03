package es.experis.arqueopterix.policyserver.model;

import es.experis.arqueopterix.policyserver.persist.dbmodel.Alert;

public class AlertJsonFront
{
	
	public String alertId;
	public String session;
	public String latency;
	public String jitter;
	public String bandwithUp;
	public String bandwithDown;
	public String packetloss;
	public String qoslevel;
	public String date;
	public String policyID;
	public boolean fireSLA;
	
	
	public AlertJsonFront(String alertId, String session, String latency, String jitter, String bandwithUp,
			String bandwithDown, String packetloss, String qoslevel, String date) {
		super();
		this.alertId = alertId;
		this.session = session;
		this.latency = latency;
		this.jitter = jitter;
		this.bandwithUp = bandwithUp;
		this.bandwithDown = bandwithDown;
		this.packetloss = packetloss;
		this.qoslevel = qoslevel;
		this.date = date;
	}
	
	public AlertJsonFront(String alertId, String session, String latency, String jitter, String bandwithUp,
			String bandwithDown, String packetloss, String qoslevel, String date, String policyID, boolean fireSLA) {
		super();
		this.alertId = alertId;
		this.session = session;
		this.latency = latency;
		this.jitter = jitter;
		this.bandwithUp = bandwithUp;
		this.bandwithDown = bandwithDown;
		this.packetloss = packetloss;
		this.qoslevel = qoslevel;
		this.date = date;
		this.policyID = policyID;
		this.fireSLA = fireSLA;
	}

	public AlertJsonFront() {
			
	}

	public String getLatency() {
		return latency;
	}
	public void setLatency(String latency) {
		this.latency = latency;
	}
	public String getJitter() {
		return jitter;
	}
	public void setJitter(String jitter) {
		this.jitter = jitter;
	}
	public String getPacketloss() {
		return packetloss;
	}
	public void setPacketloss(String packetloss) {
		this.packetloss = packetloss;
	}
	public String getQoslevel() {
		return qoslevel;
	}
	public void setQoslevel(String qoslevel) {
		this.qoslevel = qoslevel;
	}
	
	
	public String getBandwithUp() {
		return bandwithUp;
	}
	public void setBandwithUp(String bandwithUp) {
		this.bandwithUp = bandwithUp;
	}
	public String getBandwithDown() {
		return bandwithDown;
	}
	public void setBandwithDown(String bandwithDown) {
		this.bandwithDown = bandwithDown;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAlertId() {
		return alertId;
	}
	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}
	

	public String getPolicyID() {
		return policyID;
	}

	public void setPolicyID(String policyID) {
		this.policyID = policyID;
	}

	public boolean getFireSLA() {
		return fireSLA;
	}

	public void setFireSLA(boolean fireSLA) {
		this.fireSLA = fireSLA;
	}

	public void convertToAlertJsonFront(Alert alert) {
		
		this.alertId = String.valueOf(alert.getIdalert());
		this.session = String.valueOf(alert.getSession().getIdsession());
		this.latency = String.valueOf(alert.getLatency());
		this.jitter = String.valueOf(alert.getJitter());
		this.bandwithUp = String.valueOf(alert.getBandwidthup());
		this.bandwithDown = String.valueOf(alert.getBandwidthdown());
		this.packetloss = String.valueOf(alert.getPacketloss());
		this.qoslevel = String.valueOf(alert.getQoslevel());		
		this.date = alert.getReceptionDate().toString();
		if(alert.getPolicy() == null) {
			this.policyID = "";
		} else {
			this.policyID = String.valueOf(alert.getPolicy().getIdPolicy());
		}
		
		this.fireSLA = alert.isFireSLA();
	}
		
	

}
