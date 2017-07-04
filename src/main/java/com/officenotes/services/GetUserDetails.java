package com.officenotes.services;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.officenotes.entities.User;
import com.officenotes.gcm.Apns;
import com.officenotes.model.NoteRequest;
import com.officenotes.persistence.HibernateUtil;

public class GetUserDetails  implements NoteService<User>{

	
	public User service(NoteRequest request) {
		
		User userdetails=new User();
		if(request.getEntities().containsKey("GETUSERDETAILS"))
		{
		
		
			Map<String,String> usermap = (Map<String,String>) request
					.getEntities().get("GETUSERDETAILS");
		
			
			String email= usermap.get("email");
			String pwd= usermap.get("password");
			System.out.println("Login called for user:"+email+",pwd:"+pwd);
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();			
			
			Query query = session
					.createQuery("from User where email= :email and password= :pwd");
			query.setParameter("email",email);
			query.setParameter("pwd",pwd);
			
			if(query.list().size() >=1)
			{
			userdetails = (User)query.list().get(0);
			session.getTransaction().commit();		
			}
		 
		
	
	}
		return userdetails;
	

}
}
