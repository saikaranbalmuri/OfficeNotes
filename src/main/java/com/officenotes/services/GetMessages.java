package com.officenotes.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.officenotes.entities.Announcement;
import com.officenotes.entities.Device;
import com.officenotes.entities.Messages;
import com.officenotes.model.NoteRequest;
import com.officenotes.persistence.HibernateUtil;

public class GetMessages  implements NoteService<List<Messages>>{
	
	public List<Messages> service(NoteRequest request) {
		
		List<Messages> messages = new ArrayList<Messages>();
			
		if(request.getEntities().containsKey("GETMESSAGES"))
		{
			
			Map<String,String> devicemap = (Map<String,String>) request
					.getEntities().get("GETMESSAGES");
			Integer userid = Integer.parseInt(devicemap.get("userid"));
			
			
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			
			Query query = session
					.createQuery("from Messages where userid= :userid order by timecreated DESC");

			query.setParameter("userid",userid);
			List result = (List)query.list();
	
			System.out.println("list:"+ result);
			
			messages =(List<Messages>) query.list();
			session.getTransaction().commit();
			
		}
		return messages;
}
}