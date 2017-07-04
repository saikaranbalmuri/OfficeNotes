package com.officenotes.services;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.officenotes.entities.Device;
import com.officenotes.entities.User;
import com.officenotes.model.NoteRequest;
import com.officenotes.persistence.HibernateUtil;

public class GetDeviceDetails implements NoteService<Device>{

	public Device service(NoteRequest request) {
		Device device = new Device();
		if(request.getEntities().containsKey("GETDEVICEDETAILS"))
		{
			
			Map<String,String> devicemap = (Map<String,String>) request
					.getEntities().get("GETDEVICEDETAILS");
				
			long userid = Long.parseLong(devicemap.get("userid"));
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Query query = session
			.createQuery("from Device where userid= :userid");
			query.setParameter("userid",userid);
			
			device = (Device)query.list().get(0);
			session.getTransaction().commit();
		}
		
		
		
		
		// TODO Auto-generated method stub
		return device;
	}

	
	
	
}
