package com.example.sustattendancemanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

class JArrayIndex {

	private JSONArray array;
	private JSONObject obj;
	private int position;
	JArrayIndex(final String jsonstring) throws JSONException 
	{
		//int bytes = jsonstring.getBytes().length;
		//Log.i("size",""+bytes);
		
		array=new JSONArray(jsonstring);
		position=-1;
	}
	public JArrayIndex setJObjectPosition(int pos)
	{
		position=pos;
		return this;
	}
	public String getValue(String key) throws JSONException
	{
		obj= array.getJSONObject(position);
		if(obj.isNull(key))
			return "null";
		return obj.getString(key);
	}
	public int getLength()
	{
		return array.length();
	}
	
}
