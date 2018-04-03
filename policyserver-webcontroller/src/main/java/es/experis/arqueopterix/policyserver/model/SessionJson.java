package es.experis.arqueopterix.policyserver.model;


import es.experis.arqueopterix.policyserver.persist.dbmodel.Session;

public class SessionJson
{
	
	public String sessionID;
	public String originIP;
	public String destinationIP;
	public String portOriginIP;
	public String portDestinationIP;
	public String anyAlarms;
	
	public SessionJson()
	{}
	
	public SessionJson(String sessionID, String originIP, String destinationIP, String portOriginIP,
			String portDestinationIP) {
		super();
		this.sessionID = sessionID;
		this.originIP = originIP;
		this.destinationIP = destinationIP;
		this.portOriginIP = portOriginIP;
		this.portDestinationIP = portDestinationIP;
	}	
	
	
	public SessionJson(String sessionID, String originIP, String destinationIP, String portOriginIP,
			String portDestinationIP, String anyAlarms) {
		super();
		this.sessionID = sessionID;
		this.originIP = originIP;
		this.destinationIP = destinationIP;
		this.portOriginIP = portOriginIP;
		this.portDestinationIP = portDestinationIP;
		this.anyAlarms = anyAlarms;
	}

	public String getOriginIP() {
		return originIP;
	}
	public void setOriginIP(String originIP) {
		this.originIP = originIP;
	}
	public String getDestinationIP() {
		return destinationIP;
	}
	public void setDestinationIP(String destinationIP) {
		this.destinationIP = destinationIP;
	}
	public String getPortOriginIP() {
		return portOriginIP;
	}
	public void setPortOriginIP(String portOriginIP) {
		this.portOriginIP = portOriginIP;
	}
	public String getPortDestinationIP() {
		return portDestinationIP;
	}
	public void setPortDestinationIP(String portDestinationIP) {
		this.portDestinationIP = portDestinationIP;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	
	
	public String getAnyAlarms() {
		return anyAlarms;
	}

	public void setAnyAlarms(String anyAlarms) {
		this.anyAlarms = anyAlarms;
	}

	@Override
	public String toString() {
		return "SessionJson [sessionID=" + sessionID + ", originIP=" + originIP + ", destinationIP=" + destinationIP
				+ ", portOriginIP=" + portOriginIP + ", portDestinationIP=" + portDestinationIP + "]";
	}
	
	
	public Session convertToSessionEntity()
	{
		Session sess = new Session();
		
		sess.setIdsession(Integer.parseInt(this.getSessionID()));
		sess.setDestination(this.getDestinationIP());
		sess.setDestinationPort(this.getPortDestinationIP());	
		sess.setOrigin(this.getOriginIP());
		sess.setOriginPort(this.getPortOriginIP());
		
		return sess;
	}	
	
	
	

}
