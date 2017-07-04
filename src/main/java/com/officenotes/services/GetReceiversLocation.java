package com.officenotes.services;

import java.util.Map;

import com.officenotes.model.NoteRequest;

public class GetReceiversLocation implements NoteService {

	public Object service(NoteRequest request) {
		
		
		
		if(request.getEntities().containsKey("GETRECEIVERSLOCATION"))
		{
			
			Map<String,String> devicemap = (Map<String,String>) request
					.getEntities().get("GETRECEIVERSLOCATION");
				
		}
		
		// TODO Auto-generated method stub
		return null;
	}
	

}
