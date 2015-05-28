package com.example.sustattendancemanager;

import android.util.Log;

class UpdateAttendanceThread extends Thread
{
	private String attendance,uri;
	private NameRegAttend objectToUpdate;
	private UpdateAttendanceListener listener;
	private Key_value keyVal[];
	private int position;
	UpdateAttendanceThread(UpdateAttendanceListener listener,int pos,boolean isPresent)
	{
		this.listener =listener;
		position =pos;
		attendance =listener.attendanceNumber(isPresent);
		objectToUpdate =listener.objectToModify(pos);
		uri =listener.getUri();
		keyVal =listener.getKeyVal(attendance,objectToUpdate.getReg());
			
		
	}
	
	public void run()
	{
		try
		{
			Httprequest req=new Httprequest(uri);
		
			req.setKeyValues(keyVal);
			req.execute();
			String result=req.getResponse();
			if(result.compareTo("success")==0)
			{
				Log.i("Tanvy", "success");
			}
			else
			{
				objectToUpdate.setAttendance("null");
				listener.getErrorMessage(new Exception(result));
				
			}
			
		}
		catch(Exception e)
		{
			objectToUpdate.setAttendance("null");
			listener.getErrorMessage(e);
		}
		
	}
	
	
	
}