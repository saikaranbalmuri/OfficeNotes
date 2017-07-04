package com.officenotes.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.officenotes.entities.Announcement;
import com.officenotes.model.NoteRequest;
import com.officenotes.persistence.HibernateUtil;

public class GetUserAnnouncements implements NoteService<List<Announcement>> {

	public List<Announcement> service(NoteRequest request) {
		
		List<Announcement> announcements = new ArrayList<Announcement>();
		if(request.getEntities().containsKey("GETANNOUNCEMENTS"))
		{	
			Map<String,String> annmap = (Map<String,String>) request
					.getEntities().get("GETANNOUNCEMENTS");
			
			Integer userid= Integer.parseInt(annmap.get("userid"));
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();		
			Query query = session
					.createQuery("from Announcement where userid= :userid order by timecreated DESC");
			query.setParameter("userid",userid);
			List result = (List)query.list();
			System.out.println("list:"+ result);
			announcements =(List<Announcement>) query.list();
			session.getTransaction().commit();

		}
		
		
		return announcements;
	}

}
