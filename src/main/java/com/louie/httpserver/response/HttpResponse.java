package com.louie.httpserver.response;

import java.util.Date;

public class HttpResponse {
	private Integer status;
	private String protocol;
	private String version;
	private Date date;
	private String contentType;
	private Integer contentLength;
	private String body;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Integer getContentLength() {
		return contentLength;
	}
	public void setContentLength(Integer contentLength) {
		this.contentLength = contentLength;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
