package com.officenotes.services;

import java.util.Map;

import org.hibernate.Session;

import com.officenotes.persistence.HibernateUtil;

import com.officenotes.entities.User;
import com.officenotes.model.NoteRequest;

public class RegistrationService implements NoteService<User> {
	

	public User service(NoteRequest request) {
		User obj  = new User();
		
		if(request.getEntities().containsKey("REG"))
		{
		
	
		Map<String,String> usermap = (Map<String,String>) request
				.getEntities().get("REG");
		obj.setName(usermap.get("name"));
		obj.setDesignation(usermap.get("designation"));
		obj.setOfficehours(usermap.get("officehours"));
		obj.setEmail(usermap.get("email"));
		obj.setPassword(usermap.get("password"));
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();			
		session.save(obj);
		
		session.getTransaction().commit();
		
		
	
	   }
		return obj;
	
}
}