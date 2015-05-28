package com.example.sustattendancemanager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	private String userid,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		Button login =(Button) findViewById(R.id.login);
		login.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		userid= ((EditText) findViewById(R.id.username)).getText().toString();
		password= ((EditText) findViewById(R.id.password)).getText().toString();
		new loginclass().execute();
		
	}
	private class loginclass extends AsyncTask<Void,String,Void>
	{

		private ProgressDialog loadingdialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			loadingdialog = new ProgressDialog(MainActivity.this);
			loadingdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			loadingdialog.setMessage("Please Wait !!");
			loadingdialog.show();
			
		}
		
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			try
			{
				Httprequest req=new Httprequest(StaticDatas.uri);
				Key_value user=new Key_value("user",userid);
				Key_value pass=new Key_value("pass",password);
				Key_value about=new Key_value("command","about");
				req.setKeyValues(user,pass,about);
				req.execute();
				String result=req.getResponse();
				Intent welcome =new Intent(MainActivity.this,WelcomeWindow.class);
				welcome.putExtra("welcome", result);
				startActivity(welcome);
				finish();
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
	
	
}
