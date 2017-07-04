package com.officenotes.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.hibernate.Session;

import com.officenotes.entities.Messages;
import com.officenotes.gcm.Apns;
import com.officenotes.gcm.Content;
import com.officenotes.gcm.IosContent;
import com.officenotes.gcm.Post2GCM;
import com.officenotes.model.NoteRequest;
import com.officenotes.persistence.HibernateUtil;
import com.officenotes.util.NotesUtil;

public class CreateMessages implements NoteService<Messages> {

	public Messages service(NoteRequest request) {

		Messages message = new Messages();
		String regid = null,ostype=null;
		Map<String, String> messagemap=new HashMap<String, String>();
		if (request.getEntities().containsKey("CREATEMESSAGE")) {

			messagemap= (Map<String, String>) request.getEntities().get("CREATEMESSAGE");

		}
		
		message.setUserid(Integer.parseInt(messagemap.get("userid")));
		message.setMessage_text(messagemap.get("message_text"));
		
		
		java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
		java.util.Date dt=new Date();
		date.setTime(new java.util.Date().getTime());
		message.setTimecreated(dt);
		
		message.setEmail(messagemap.get("email"));
		message.setUrl(messagemap.get("url"));
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();			
		session.save(message);
		
		Long userid = Long.parseLong(messagemap.get("userid"));
		Query query = session
				.createQuery("select device_id,device_os from Device where userid= :userid and device_type =:dtype");
		query.setParameter("userid",userid);
		query.setParameter("dtype","Sender");
		
		List querylist = (List) query.list();
		System.out.println("Message saved:"+dt.toLocaleString());
		
		
		for (Iterator it = querylist.iterator(); it.hasNext(); ) {
               Object[] myResult = (Object[]) it.next();
               regid =  (String) myResult[0];
               ostype = (String) myResult[1];
               System.out.println( "REGID: " + regid + "\nOS TYPE: " +ostype  );
            }

		session.getTransaction().commit();
		ObjectMapper mapper=new ObjectMapper();
		String response="";
		try {
			response=mapper.writeValueAsString(message);
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
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if(ostype.equals("Android"))
		{
			Content content = new Content();
			content.addRegId(regid);
			content.createData("CREATEMESSAGE",response);
	        Post2GCM.post(content);

		}
		else if(ostype.equals("iOS"))
		{
		IosContent<Messages> content = new IosContent<>();
		content.addRegId(regid);
		content.createData("CREATEMESSAGE",message);
		Apns<Messages> apns =new Apns<>();	
		apns.pushmessage(content);
		}
	

		return message;
	}
}
