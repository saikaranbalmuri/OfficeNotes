package com.officenotes.services;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.officenotes.entities.Device;
import com.officenotes.entities.User;
import com.officenotes.gcm.Apns;
import com.officenotes.gcm.Content;
import com.officenotes.gcm.IosContent;
import com.officenotes.gcm.Post2GCM;
import com.officenotes.model.NoteRequest;
import com.officenotes.persistence.HibernateUtil;

public class Updateavailability implements NoteService{

	public Object service(NoteRequest request) {
		
		Device device = new Device();
		String regid = null,ostype=null;
		if(request.getEntities().containsKey("UPDATEAVAILABILITY"))
		{
			
			Map<String,String> availabilitymap = (Map<String,String>) request
					.getEntities().get("UPDATEAVAILABILITY");
			
			long userid = Long.parseLong(availabilitymap.get("userid"));
			String Availability = availabilitymap.get("available");
			System.out.println("value is:"+Availability);
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();			
			
			Query query = session
					.createQuery("Select device_id,device_os from Device where userid= :userid and device_type =:dtype");
			query.setParameter("userid",userid);
			query.setParameter("dtype","Receiver");
			
			List result = (List) query.list();
			
			//String deviceType =(String)query.list().get(1);
			System.out.println("strin is:"+result);
		//	System.out.println("strin is:"+deviceType);	
			for (Iterator it = result.iterator(); it.hasNext(); ) {
	               Object[] myResult = (Object[]) it.next();
	               regid =  (String) myResult[0];
	               ostype = (String) myResult[1];
	               System.out.println( "REGID: " + regid + "\nOS TYPE: " +ostype  );   
	            }
			session.getTransaction().commit();	
	
		
			if(ostype.equals("Android"))
			{
				Content content = new Content();
				content.addRegId(regid);
				content.createData("Availability",Availability);
		        Post2GCM.post(content);
			}

			else if(ostype.equals("iOS"))
			{
	        IosContent<String> content = new IosContent<>();
			content.addRegId(regid);
			content.createData("Availability",Availability);
			Apns<String> apns =new Apns<>();			
			apns.pushmessage(content);
			}
	        
	        
		}
		
		return null;
	}

	
	
}
