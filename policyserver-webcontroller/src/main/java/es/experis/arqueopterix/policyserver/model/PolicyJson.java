package es.experis.arqueopterix.policyserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.experis.arqueopterix.policyserver.persist.dbmodel.Policies;

public class PolicyJson {
	
	/**
	 * IP de destino
	 */
	@JsonProperty("endPoint")
	private String endPoint;
	/**
	 * Número de sesiones a las que se asegura el SLA
	 */
	@JsonProperty("numSessions")
	private String numSessions;
	
	/**
	 * Ancho de banda asegurado de subida. Kbps
	 */
	@JsonProperty("bandwidthUP")
	private String bandwidthUP;
	/**
	 * Ancho de banda asegurado de bajada. Kbps
	 */
	@JsonProperty("bandwidthDOWN")
	private String bandwidthDOWN;
	
	/**
	 * Ancho de banda asegurado de subida
	 */
	@JsonProperty("pingMaxDelay")
	private String pingMaxDelay;
	
	/**
	 * Identificador del policy
	 */
	@JsonProperty("policyID")
	private String policyID;
	
	public PolicyJson()
	{}
	
	
	
	public PolicyJson(String endPoint, String numSessions, String bandwidthUP, String bandwidthDOWN,
			String pingMaxDelay, String policyID) {
		super();
		this.endPoint = endPoint;
		this.numSessions = numSessions;
		this.bandwidthUP = bandwidthUP;
		this.bandwidthDOWN = bandwidthDOWN;
		this.pingMaxDelay = pingMaxDelay;
		this.policyID = policyID;
	}



	public String getPolicyID() {
		return policyID;
	}
	public void setPolicyID(String policyID) {
		this.policyID = policyID;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getNumSessions() {
		return numSessions;
	}
	public void setNumSessions(String numSessions) {
		this.numSessions = numSessions;
	}
	public String getBandwidthUP() {
		return bandwidthUP;
	}
	public void setBandwidthUP(String bandwidthUP) {
		this.bandwidthUP = bandwidthUP;
	}
	public String getBandwidthDOWN() {
		return bandwidthDOWN;
	}
	public void setBandwidthDOWN(String bandwidthDOWN) {
		this.bandwidthDOWN = bandwidthDOWN;
	}
	public String getPingMaxDelay() {
		return pingMaxDelay;
	}
	public void setPingMaxDelay(String pingMaxDelay) {
		this.pingMaxDelay = pingMaxDelay;
	}
	@Override
	public String toString() {
		return "PolicyJson [endPoint=" + endPoint + ", numSessions=" + numSessions + ", bandwidthUP=" + bandwidthUP
				+ ", bandwidthDOWN=" + bandwidthDOWN + ", pingMaxDelay=" + pingMaxDelay + "]";
	}
	
		
	public Policies convertToPolicyEntity()
	{
		Policies policy = new Policies();
		policy.setIdPolicy(Integer.parseInt(this.getPolicyID()));
		policy.setEndPoint(this.getEndPoint());
		policy.setNumSessions(Integer.parseInt(this.getNumSessions()));
		policy.setPingMaxDelay(Float.parseFloat(this.getPingMaxDelay()));
		policy.setBandwidthDOWN(Float.parseFloat(this.getBandwidthDOWN()));
		policy.setBandwidthUP(Float.parseFloat(this.getBandwidthUP()));
		return policy;
	}
	
	

}
