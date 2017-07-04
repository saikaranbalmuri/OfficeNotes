package com.officenotes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String masterKey;
	private List<String> addons;
	private Map<String,Object> entities;
	
	
	
	public NoteRequest() {
		super();
		this.addons=new ArrayList<>();
		this.entities=new HashMap<>();
	}
	public String getMasterKey() {
		return masterKey;
	}
	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}
	public List<String> getAddons() {
		return addons;
	}
	public void setAddons(List<String> addons) {
		this.addons = addons;
	}
	public Map<String, Object> getEntities() {
		return entities;
	}
	public void setEntities(Map<String, Object> entities) {
		this.entities = entities;
	}
}
