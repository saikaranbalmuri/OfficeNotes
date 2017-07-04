package com.officenotes.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.officenotes.constants.ServiceKeys;
import com.officenotes.entities.Announcement;
import com.officenotes.entities.Device;
import com.officenotes.entities.Messages;
import com.officenotes.entities.User;
import com.officenotes.model.NoteRequest;
import com.officenotes.model.NoteResponse;
import com.officenotes.services.AddDevice;
import com.officenotes.services.CreateAnnouncements;
import com.officenotes.services.CreateMessages;
import com.officenotes.services.DeleteAnnouncement;
import com.officenotes.services.GetDeviceDetails;
import com.officenotes.services.GetMessages;
import com.officenotes.services.GetUserAnnouncements;
import com.officenotes.services.GetUserDetails;
import com.officenotes.services.NoteService;
import com.officenotes.services.RegistrationService;
import com.officenotes.services.Updateavailability;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path(value="/do")
public class GenericServiceController {
	
	private static Map<String, NoteService<?>> serviceMap = new HashMap<String, NoteService<?>>();
	private static ObjectMapper mapper=new ObjectMapper();
	
	public void initServiceMap(){
		if (serviceMap.isEmpty()) {
			serviceMap.put(ServiceKeys.GETUSERDETAILS, new GetUserDetails());
			
			System.out.println("Generic Service ControllerInitialized");
	}
	}
	
	@POST
	@Path("/user")
	public Response doRegistration(String request) throws JsonGenerationException, JsonMappingException, IOException
	{
		//RegistrationService service=new RegistrationService();
		
		NoteRequest noterequest =null;
		String output="";
		User user=null;
		 try {
			noterequest = mapper.readValue(request, NoteRequest.class);
			System.out.println("Request url:"+noterequest);
			RegistrationService registrationService=new RegistrationService();
			user=registrationService.service(noterequest);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		 NoteResponse noteresponse = new NoteResponse("success");
		 
		 noteresponse.getKeys().add(noterequest.getMasterKey());
		 
		 noteresponse.getEntities().put(noterequest.getMasterKey(), user);
		 
		 output = mapper.writeValueAsString(noteresponse);
		 
		 return Response.status(200).entity(output).build();
		
	}
	
	@POST
	@Path("/getuserdetails")
	/*public Response getuserdetails(String request) throws JsonParseException, JsonMappingException, IOException{
		System.out.println("Generic Service ControllerInitialized");
		
		return executeService(request);
	}*/
	public Response doUserDetails(String request) throws JsonGenerationException, JsonMappingException, IOException 
	{
		NoteRequest noterequest =null;
		String output="";
		User user=null;
		
		
		try {
			noterequest = mapper.readValue(request, NoteRequest.class);
			System.out.println("Request url:"+noterequest);
			GetUserDetails userdetails =new GetUserDetails();
			user = userdetails.service(noterequest);
			System.out.println("Till here");
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NoteResponse noteresponse = new NoteResponse("success");
		
		noteresponse.getKeys().add(noterequest.getMasterKey());
		noteresponse.getEntities().put(noterequest.getMasterKey(), user);
	    output = mapper.writeValueAsString(noteresponse);
		return Response.status(200).entity(output).build();
		
	}
	
	public Response executeService(String request) {
		NoteRequest noterequest =null;
		System.out.println("in execute service");
			try {
				noterequest = mapper.readValue(request, NoteRequest.class);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
		
		
		Object response = null;
		try {
			response = serviceMap.get(noterequest.getMasterKey()).service(noterequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NoteResponse noteresponse = new NoteResponse("success");
		noteresponse.getKeys().add(noterequest.getMasterKey());
		noteresponse.getEntities().put(noterequest.getMasterKey(), response);
		String output = null;
		try {
			output = mapper.writeValueAsString(noteresponse);
			System.out.println("Output"+ output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(200).entity(output).build();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@POST
	@Path("/announcements")
	public Response doAnnouncements(String request) throws JsonGenerationException, JsonMappingException, IOException
	{
		//RegistrationService service=new RegistrationService();
		
		NoteRequest noterequest =null;
		String output="";
		Announcement ann =null;
		 try {
			noterequest = mapper.readValue(request, NoteRequest.class);
			CreateAnnouncements createann = new CreateAnnouncements();
			ann = (Announcement) createann.service(noterequest); 
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		 NoteResponse noteresponse = new NoteResponse("success");
		 noteresponse.getKeys().add(noterequest.getMasterKey());
		 noteresponse.getEntities().put(noterequest.getMasterKey(), ann);
		 output = mapper.writeValueAsString(noteresponse);
		 
		 return Response.status(200).entity(output).build();
		
	}
	@POST
	@Path("/getannouncements")
	public Response doGetAnnouncements(String request) throws JsonGenerationException, JsonMappingException, IOException
	{
		//RegistrationService service=new RegistrationService();
		
		NoteRequest noterequest =null;
		String output="";
		List<Announcement> ann =null;
		 try {
			noterequest = mapper.readValue(request, NoteRequest.class);
			System.out.println(request);
			GetUserAnnouncements getann = new GetUserAnnouncements();
			ann =(List<Announcement>) getann.service(noterequest); 
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		 NoteResponse noteresponse = new NoteResponse("success");
		 
		 noteresponse.getKeys().add(noterequest.getMasterKey());
		 
		 noteresponse.getEntities().put(noterequest.getMasterKey(), ann);
		 
		 output = mapper.writeValueAsString(noteresponse);
		 
		 return Response.status(200).entity(output).build();
		
	}
	
	@POST
	@Path("/updateavailability")
	public Response doUpdatAvailability(String request) throws JsonGenerationException, JsonMappingException, IOException
	{
		//RegistrationService service=new RegistrationService();
		
		NoteRequest noterequest =null;
		String output="";
		
		 try {
			noterequest = mapper.readValue(request, NoteRequest.class);
			System.out.println("Request url:"+request);
			Updateavailability uavailability= new Updateavailability();
			uavailability.service(noterequest);
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		 NoteResponse noteresponse = new NoteResponse("success");
		 
		 
		 
		
		 
		 output = mapper.writeValueAsString(noteresponse);
		 
		 return Response.status(200).entity(output).build();
		
	}
	

@POST
@Path("/adddevice")
public Response doAddDevice(String request) throws JsonGenerationException, JsonMappingException, IOException
{
	//RegistrationService service=new RegistrationService();
	
	NoteRequest noterequest =null;
	String output="";
	Device device=null;
	
	 try {
		noterequest = mapper.readValue(request, NoteRequest.class);
		AddDevice adddevice= new AddDevice();
		System.out.println("Request url:"+request);
		device = (Device) adddevice.service(noterequest);
		
	} catch (JsonParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block 
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	 NoteResponse noteresponse = new NoteResponse("success");
	 
	 
	 noteresponse.getKeys().add(noterequest.getMasterKey());
	 
	 noteresponse.getEntities().put(noterequest.getMasterKey(), device);
	 
	 output = mapper.writeValueAsString(noteresponse);
	 
	 return Response.status(200).entity(output).build();
	
}


@POST
@Path("/getdevicedetails")
public Response doGetDeviceDetails(String request) throws JsonGenerationException, JsonMappingException, IOException
{
	//RegistrationService service=new RegistrationService();
	
	NoteRequest noterequest =null;
	String output="";
	Device device=null;
	
	 try {
		noterequest = mapper.readValue(request, NoteRequest.class);
		GetDeviceDetails getdevice= new GetDeviceDetails();
		device = (Device) getdevice.service(noterequest);
		
	} catch (JsonParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block 
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();  
	}

	 NoteResponse noteresponse = new NoteResponse("success");
	 
	 
	 noteresponse.getKeys().add(noterequest.getMasterKey());
	 
	 noteresponse.getEntities().put(noterequest.getMasterKey(), device);
	 
	 output = mapper.writeValueAsString(noteresponse);
	 
	 return Response.status(200).entity(output).build();
	
}

@POST
@Path("/deleteannouncements")
public Response doDeleteAnnouncements(String request) throws JsonGenerationException, JsonMappingException, IOException
{
	//RegistrationService service=new RegistrationService();
	
	NoteRequest noterequest =null;
	String output="";
	Announcement ann =null;
	 try {
		noterequest = mapper.readValue(request, NoteRequest.class);
		System.out.println(request);
		DeleteAnnouncement deleteann = new DeleteAnnouncement();
		ann = (Announcement) deleteann.service(noterequest); 
		
	} catch (JsonParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block 
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	 NoteResponse noteresponse = new NoteResponse("success");
	 
	 noteresponse.getKeys().add(noterequest.getMasterKey());
	 
	 noteresponse.getEntities().put(noterequest.getMasterKey(), ann);
	 
	 output = mapper.writeValueAsString(noteresponse);
	 
	 return Response.status(200).entity(output).build();
	
}




@POST
@Path("/createmessage")
public Response doCreateMeaage(String request) throws JsonGenerationException, JsonMappingException, IOException
{
	//RegistrationService service=new RegistrationService();
	
	NoteRequest noterequest =null;
	String output="";
	Messages message =null;
	 try {
		noterequest = mapper.readValue(request, NoteRequest.class);
		System.out.println("Request url:"+request);
		CreateMessages createmessage = new CreateMessages();
		
		message = (Messages)createmessage.service(noterequest); 
		
		
	} catch (JsonParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block 
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	 NoteResponse noteresponse = new NoteResponse("success");
	 
	 noteresponse.getKeys().add(noterequest.getMasterKey());
	 
	 noteresponse.getEntities().put(noterequest.getMasterKey(), message);
	 
	 output = mapper.writeValueAsString(noteresponse);
	 
	 return Response.status(200).entity(output).build();
	
}



@POST
@Path("/getmessages")
public Response doGetMessages(String request) throws JsonGenerationException, JsonMappingException, IOException
{
	//RegistrationService service=new RegistrationService();
	
	NoteRequest noterequest =null;
	String output="";
	List<Messages> message =null;
	 try {
		noterequest = mapper.readValue(request, NoteRequest.class);
		System.out.println(request);
		GetMessages getmessages = new GetMessages();
		message =(List<Messages>)getmessages.service(noterequest); 
		
	} catch (JsonParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block 
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	 NoteResponse noteresponse = new NoteResponse("success");
	 
	 noteresponse.getKeys().add(noterequest.getMasterKey());
	 
	 noteresponse.getEntities().put(noterequest.getMasterKey(), message );
	 
	 output = mapper.writeValueAsString(noteresponse);
	 
	 return Response.status(200).entity(output).build();
	
}

@POST
@Path("/upload/{uid}")
@Consumes(MediaType.MULTIPART_FORM_DATA)
public Response uploadFile(@PathParam("uid") String uid,
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
		System.out.println("upload called by user :"+uid);
		String url="ON_"+uid+"_"+new Date().getTime()+".mp4";
		String uploadedFileLocation = "Z:/ONotes/videos/"+url;
		
		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);
		System.out.println("file saved");
		
		
		NoteResponse noteresponse = new NoteResponse("success");
		CreateMessages createMessages=new CreateMessages();
		
		NoteRequest request=new NoteRequest();
		request.setMasterKey("CREATEMESSAGE");
		Map<String, String> keys=new HashMap<>();
		keys.put("userid",uid);
		keys.put("email","");
		keys.put("url",url);
		keys.put("message_text","");
		
		request.getEntities().put("CREATEMESSAGE", keys);
		
		createMessages.service(request);
		String output=null;
		try {
			output = mapper.writeValueAsString(noteresponse);
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

		return Response.status(200).entity(output).build();

	}

	@GET
    @Path("/video/{name}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getTextFile(@PathParam("name") String fname) {
		System.out.println("Trying to get video :"+fname);
		File file = new File("Z:/ONotes/videos/"+fname);
		System.out.println("File"+file.getAbsolutePath());
        
		ResponseBuilder response = Response.ok(file,MediaType.APPLICATION_OCTET_STREAM);
        //response.header("Content-Disposition", "attachment; filename=\""+fname+"\"");
        return response.build();
		
//		return new StreamingOutput() {
//			
//			@Override
//			public void write(OutputStream output) throws IOException, WebApplicationException {
//				// TODO Auto-generated method stub
//				FileInputStream inputStream=new FileInputStream(file);
//				int c;
//				while ((c = inputStream.read()) != -1) {
//					output.write(inputStream.read());
//		         }
//				
//			}
//		};
    }
	
	
	@POST
	@Path("/uploadfile/{uid}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadtextFiles(@PathParam("uid") String uid,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {
			System.out.println("upload called by user :"+uid);
			String url="ON_"+uid+"_"+new Date().getTime()+".txt";
			String uploadedFileLocation = "Z:/bidkraft/dissertationfiles/"+url;
			
			writeToFile(uploadedInputStream, uploadedFileLocation);
			System.out.println("file saved");
		
		
			NoteResponse noteresponse = new NoteResponse("success");
			String output=null;
			try {
				output = mapper.writeValueAsString(noteresponse);
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

			return Response.status(200).entity(output).build();
		}
	
	
	
	
	


//save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
		String uploadedFileLocation) {

		try {
			OutputStream out = new FileOutputStream(new File(
					uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));  
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}


}


