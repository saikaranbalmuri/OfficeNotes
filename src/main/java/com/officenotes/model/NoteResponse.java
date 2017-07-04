package com.officenotes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteResponse {
	
	private String status;
	private List<String> keys;
	private Map<String,Object> entities;
	
	public NoteResponse(String status)
	{
		this.status=status;
		this.keys=new ArrayList<String>();
		this.entities=new HashMap<String,Object>();
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getKeys() {
		return keys;
	}
	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
	public Map<String, Object> getEntities() {
		return entities;
	}
	public void setEntities(Map<String, Object> entities) {
		this.entities = entities;
	}

}
