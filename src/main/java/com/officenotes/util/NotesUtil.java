package com.officenotes.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NotesUtil {

	public static Date getSQLDate()
	{
		java.util.Date dt=new java.util.Date();
		Date date=new Date(dt.getTime());
		return date;
	}
	
	public static Date getSQLDate(String timestamp)
	{
		SimpleDateFormat dateFormat=new SimpleDateFormat("mm-dd-yyyy hh:mm:ss");
		java.util.Date dt=null;
		try {
			dt = dateFormat.parse(timestamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date date=new Date(dt.getTime());
		return date;
	}
}
