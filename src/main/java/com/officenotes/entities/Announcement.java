package com.officenotes.entities;



import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="announcement")
public class Announcement {

private int  announcementId;
private int userid;
private String title;
private String Description;
private Date expiry;
private Date timecreated;



@Id
@GeneratedValue
@Column(name="id")
public int getAnnouncementId() {
	return announcementId;
}
public void setAnnouncementId(int announcementId) {
	this.announcementId = announcementId;
}
@Column(name="userid")
public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
}
@Column(name="title")
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
@Column(name="description")
public String getDescription() {
	return Description;
}
public void setDescription(String description) {
	Description = description;
}
@Column(name="expiry")
public Date getExpiry() {
	return expiry;
}
public void setExpiry(Date expiry) {
	this.expiry = expiry;
}
@Column(name="timecreated")
public Date getTimecreated() {
	return timecreated;
}
public void setTimecreated(Date timecreated) {
	this.timecreated = timecreated;
}


	
}
