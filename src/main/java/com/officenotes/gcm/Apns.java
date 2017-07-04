package com.officenotes.gcm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsNotification;
import com.notnoop.apns.ApnsService;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;

public class Apns<T> {

	
	 public void pushmessage(IosContent<T> content){
		 String token=null;
		 String message=null;
		 ClassLoader classLoader = getClass().getClassLoader();
		 File file = new File(classLoader.getResource("/OfficeNotes.p12").getFile());
		 System.out.println("Cert Path:"+file.getAbsolutePath());
		 InputStream stream=null;
		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	ApnsService service =APNS.newService()
		    .withCert(stream,"")
		    .withSandboxDestination()
		    .build();
	
	   ObjectMapper mapper = new ObjectMapper();
	   try {
		 message = mapper.writeValueAsString(content);
		 System.out.println("iOS notification: "+message);
		 token=content.registration_ids.get(0);
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	   try {
	   //PushNotificationPayload payload=  PushNotificationPayload.fromJSON(message);
	//String payload = APNS.newPayload().alertBody(message).build();
	//String token = "0fcf809dd4fbed84a6303fa6ecdc3dd6494de3e857c322d51dff2263d5c2f608";
	service.push(token, message);
		//Push.payload(payload, stream, "", false,token );
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	
	}
	
}
