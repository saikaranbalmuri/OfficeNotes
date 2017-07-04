package com.officenotes.services;

import java.io.IOException;

import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.Session;

import com.officenotes.constants.ServiceKeys;
import com.officenotes.entities.Announcement;
import com.officenotes.entities.User;
import com.officenotes.gcm.Apns;
import com.officenotes.gcm.Content;
import com.officenotes.gcm.IosContent;
import com.officenotes.gcm.Post2GCM;
import com.officenotes.model.NoteRequest;
import com.officenotes.persistence.HibernateUtil;
import com.officenotes.util.NotesUtil;

public class CreateAnnouncements implements NoteService {

	 public Announcement service(NoteRequest request) {   

		
		Announcement announcment = new Announcement();
		String regid = null,ostype=null;
		if(request.getEntities().containsKey(ServiceKeys.CREATEANNOUNCEMENT))
		{
		
			Map<String,String> annmap = (Map<String,String>) request
					.getEntities().get(ServiceKeys.CREATEANNOUNCEMENT);
		
			
			
			announcment.setUserid(Integer.parseInt(annmap.get("userid")));
			announcment.setTitle(annmap.get("title"));
			announcment.setDescription(annmap.get("description"));
			announcment.setExpiry(NotesUtil.getSQLDate(annmap.get("expiry")));
			
			
			java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
			java.util.Date dt=new Date();
			date.setTime(new java.util.Date().getTime());
			announcment.setTimecreated(dt);
		
		
			//announcment.setTimecreated(NotesUtil.getSQLDate(formattedDate));
			

			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();			
			session.save(announcment);
			
			
			long userid = Long.parseLong(annmap.get("userid"));

			Query query = session
					.createQuery("select device_id,device_os from Device where userid= :userid and device_type =:dtype");
			query.setParameter("userid",userid);
			query.setParameter("dtype","Receiver");

			List result = (List) query.list();
			
			//String deviceType =(String)query.list().get(1);
			System.out.println("Announcement create at:"+date.toString());
		//	System.out.println("strin is:"+deviceType);	
			for (Iterator it = result.iterator(); it.hasNext(); ) {
	               Object[] myResult = (Object[]) it.next();
	               regid =  (String) myResult[0];
	               ostype = (String) myResult[1];
	               System.out.println( "REGID: " + regid + "\nOS TYPE: " +ostype  );
	            }
			
			
			
			
			
			
			session.getTransaction().commit();
			
			ObjectMapper mapper=new ObjectMapper();
			String response="";
			try {
				response=mapper.writeValueAsString(announcment);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(ostype.equals("Android"))
			{
			
			Content content = new Content();
			content.addRegId(regid);
			content.createData("CREATEANNOUNCEMENT",response);
	        Post2GCM.post(content);

			}
			else if(ostype.equals("iOS"))
			{
				IosContent<Announcement> content = new IosContent<>();
				content.addRegId(regid);
				content.createData("CREATEANNOUNCEMENT",announcment);
				
				Apns<Announcement> apns =new Apns<>();			
				apns.pushmessage(content);
			}
		
		
		
	}
		return announcment;
}
}

	 

