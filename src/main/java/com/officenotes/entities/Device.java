package com.officenotes.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="device")
public class Device {

	private String device_id;
	private long userid;
	private String device_type;
	private String device_os;
	private double lat;
	private double lon;
	

	@Id
	@Column(name="device_id")
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	@Column(name="userid")
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	@Column(name="device_type")
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	@Column(name="device_os")
	public String getDevice_os() {
		return device_os;
	}
	public void setDevice_os(String device_os) {
		this.device_os = device_os;
	}
	@Column(name="lattitude")
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	@Column(name="longitude")
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	
}
