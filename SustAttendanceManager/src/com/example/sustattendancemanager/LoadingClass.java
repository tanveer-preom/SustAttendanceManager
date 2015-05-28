package com.example.sustattendancemanager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class LoadingClass extends AsyncTask<Key_value,String,Void> {

	private ProgressDialog loadingdialog;
	private String uri;
	private LoadingClassListener listener;
	LoadingClass(String uri,LoadingClassListener listener)
	{
		this.uri=uri;
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		loadingdialog = new ProgressDialog(listener.getContext());
		loadingdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		loadingdialog.setMessage("Please Wait !!");
		loadingdialog.show();
		
	}
	
	protected Void doInBackground(Key_value... arg0) {
		// TODO Auto-generated method stub
		try
		{
			Httprequest req=new Httprequest(uri);
			//Key_value user=new Key_value("user",userid);
			//Key_value pass=new Key_value("pass",password);
			//Key_value about=new Key_value("command","about");
			req.setKeyValues(arg0);
			req.execute();
			String result=req.getResponse();
			listener.onPostExecuted(result);
			
			
			
			//Intent welcome =new Intent(MainActivity.this,WelcomeWindow.class);
			//welcome.putExtra("welcome", result);
			//startActivity(welcome);
			//finish();
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Tanvy", e.getMessage());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Tanvy", e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("Tanvy", e.getMessage());
		}
		finally
		{
			loadingdialog.dismiss();
			
		}
		
		return null;
		
		
		
	}

	
	
}
