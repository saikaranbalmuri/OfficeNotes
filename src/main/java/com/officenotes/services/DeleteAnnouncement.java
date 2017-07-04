package com.officenotes.services;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.officenotes.entities.Announcement;
import com.officenotes.gcm.Apns;
import com.officenotes.gcm.Content;
import com.officenotes.gcm.IosContent;
import com.officenotes.gcm.Post2GCM;
import com.officenotes.model.NoteRequest;
import com.officenotes.persistence.HibernateUtil;

public class DeleteAnnouncement  implements NoteService {

		 public Object service(NoteRequest request) {

			
			Announcement announcment = new Announcement();
			String regid = null,ostype=null;
				if(request.getEntities().containsKey("DELETEANNOUNCEMENT"))
				{
				
					Map<String,String> annmap = (Map<String,String>) request
							.getEntities().get("DELETEANNOUNCEMENT");
					
					Integer announcementid= Integer.parseInt(annmap.get("announcementid"));
					String announcementIdValue =annmap.get("announcementid");
					Session session = HibernateUtil.getSessionFactory().openSession();
					session.beginTransaction();
					
					Query query = session
							.createQuery("delete from Announcement where announcementId= :announcementid");
					query.setParameter("announcementid",announcementid);
					query.executeUpdate();
					
					long userid = Long.parseLong(annmap.get("userid"));
					Query query1 = session
							.createQuery("select device_id,device_os from Device where userid= :userid and device_type =:dtype");
					query1.setParameter("userid",userid);
					query1.setParameter("dtype","Receiver");
					
					List querylist = (List) query1.list();
					System.out.println("strin is:"+querylist);
					

					
					for (Iterator it = querylist.iterator(); it.hasNext(); ) {
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
					content.createData("DELETEANNOUNCEMENT",announcementIdValue);
			        Post2GCM.post(content);
					
					}
					else if(ostype.equals("iOS"))
					{
					
					IosContent<String> content = new IosContent<>();
					content.addRegId(regid);
					content.createData("DELETEANNOUNCEMENT",announcementIdValue);

					Apns<String> apns =new Apns<>();			
					apns.pushmessage(content);
					}
					
					
					
				}

			return announcment;
		 }

}
