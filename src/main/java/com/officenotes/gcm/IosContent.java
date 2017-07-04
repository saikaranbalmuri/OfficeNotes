package com.officenotes.gcm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IosContent<T> {
	
	private static final long serialVersionUID = 1L;
	public List<String> registration_ids;
	public String title;
	public Map<String,T> data;

    public void addRegId(String regId){
        if(registration_ids == null)
            registration_ids = new LinkedList<String>();
        registration_ids.add(regId);
    }

    public void createData(String title, T message){
        if(data == null)
            data = new HashMap<String,T>();

        this.title=title;
        data.put("message", message);
    }

}
