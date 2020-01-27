package com.richtodd.gcp.kubclient.models;

import com.richtodd.gcp.kubserver.models.HelloWorldRestModel;

public class HelloWorldMvcModel {

	private String m_description;
	private String m_message;
	private String m_dateTime;
	private String m_hostName;
	private String m_hostAddress;
	private String m_exception;
	private String m_restUrl;
	private HelloWorldRestModel m_restModel;

	public String getDescription() {
		return m_description;
	}

	public void setDescription(String description) {
		m_description = description;
	}

	public String getMessage() {
		return m_message;
	}

	public void setMessage(String message) {
		m_message = message;
	}

	public String getDateTime() {
		return m_dateTime;
	}

	public void setDateTime(String dateTime) {
		m_dateTime = dateTime;
	}

	public String getHostName() {
		return m_hostName;
	}

	public void setHostName(String hostName) {
		m_hostName = hostName;
	}

	public String getHostAddress() {
		return m_hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		m_hostAddress = hostAddress;
	}

	public String getException() {
		return m_exception;
	}

	public void setException(String exception) {
		m_exception = exception;
	}

	public String getRestUrl() {
		return m_restUrl;
	}

	public void setRestUrl(String restUrl) {
		m_restUrl = restUrl;
	}

	public HelloWorldRestModel getRestModel() {
		return m_restModel;
	}

	public void setRestModel(HelloWorldRestModel restModel) {
		m_restModel = restModel;
	}
}
