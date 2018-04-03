package es.experis.arqueopterix.policyserver.persist.dbmodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "log")
@NamedQuery(name = "Log.findAll", query = "SELECT u FROM Log u")
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	private int logId;

	private String dateTime;
	private String alertType;
	private String description;

	public Log() {
		super();
	}

	public Log(int logId, String dateTime, String alertType, String description) {
		super();
		this.logId = logId;
		this.dateTime = dateTime;
		this.alertType = alertType;
		this.description = description;
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
