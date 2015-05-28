package com.example.sustattendancemanager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import android.util.Log;

final class Httprequest{

	private String response=null;
	private HttpClient client;
	private HttpPost post;
	Httprequest(String uri)
	{
		client =new DefaultHttpClient();
		post=new HttpPost(uri);
	}
	public void setKeyValues(Key_value... requests) throws UnsupportedEncodingException 
	{
		
		
		List<NameValuePair> pairs= new ArrayList<NameValuePair>(requests.length);
		for(int i=0;i<requests.length;i++)
		{
			pairs.add(new BasicNameValuePair(requests[i].getToken(), requests[i].getValue()));
		}
		post.setEntity(new UrlEncodedFormEntity(pairs));
		
		
	}
	public void execute() throws ClientProtocolException, IOException
	{
		ResponseHandler<String> responseHandler=new BasicResponseHandler();
        response = client.execute(post, responseHandler);
        
	}
	public String getResponse() throws NullPointerException,IOException
	{
		if(response==null)
		throw new NullPointerException("Not initialized");
		if(response.compareTo("username missmatched")==0 || response.compareTo("password missmatched")==0||response.compareTo("could not connect")==0)
		{
			//Log.i("Tanvy", "threw");
			throw new IOException(response);
		}
		//Log.i("Tanvy", response);
		return response;
	}
	

}
