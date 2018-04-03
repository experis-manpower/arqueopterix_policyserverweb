package es.experis.arqueopterix.policyserver.model;

import es.experis.arqueopterix.policyserver.persist.dbmodel.Alert;

public class AlertJson
{
	
	private SessionJson session;
	private SessionJson measurementChannel;
	private String latency;
	private String jitter;
	private String bandwithUp;
	private String bandwithDown;
	private String packetloss;
	private String qoslevel;
	
	
	public SessionJson getSession() {
		return session;
	}
	public void setSession(SessionJson session) {
		this.session = session;
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
	
	
	public Alert convertToAlertEntity() {
		Alert alert = new Alert();
		alert.setBandwidthdown(Float.parseFloat(this.getBandwithUp()));
		alert.setBandwidthup(Float.parseFloat(this.getBandwithDown()));
		alert.setJitter(Float.parseFloat(this.getJitter()));
		alert.setLatency(Float.parseFloat(this.getLatency()));
		alert.setPacketloss(Float.parseFloat(this.getPacketloss()));
		alert.setQoslevel(this.getQoslevel());
		return alert;
	}
	public SessionJson getMeasurementChannel() {
		return measurementChannel;
	}
	public void setMeasurementChannel(SessionJson measurementChannel) {
		this.measurementChannel = measurementChannel;
	}
	
	
	

}
