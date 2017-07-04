package com.officenotes.entities;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="messages")
public class Messages {
	
	private int messageid;
	private int userid;
	private String message_text;
	private Blob message_video;
	private Date timecreated;
	private String email;
	private String url;

	@Id
	@GeneratedValue
	@Column(name="messageid")
	public int getMessageid() {
		return messageid;
	}
	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}
	
	@Column(name="userid")
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	@Column(name="message_text")
	public String getMessage_text() {
		return message_text;
	}
	public void setMessage_text(String message_text) {
		this.message_text = message_text;
	}
	
	@Column(name="message_video")
	public Blob getMessage_video() {
		return message_video;
	}
	public void setMessage_video(Blob message_video) {
		this.message_video = message_video;
	}
	
	
	@Column(name="timecreated")
	public Date getTimecreated() {
		return timecreated;
	}
	public void setTimecreated(Date timecreated) {
		this.timecreated = timecreated;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	

	
}

	
		
		
		
		
		
		
		
		
		
		

	
	
	
	
	
	


