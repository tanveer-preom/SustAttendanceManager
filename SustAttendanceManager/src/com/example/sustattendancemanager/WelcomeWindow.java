package com.example.sustattendancemanager;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class WelcomeWindow extends Activity{

	private String jsonstring,name,userid,profession,department,password;
	private JArrayIndex parsedresult;
	private TextView username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_window);
		jsonstring=getIntent().getExtras().getString("welcome");
		initUiVars();
		welcomeAlertDialog();
		username.setText(userid);
		//Log.i("Tanvy", "aftha");
	}
	void initUiVars()
	{
		username= (TextView) findViewById(R.id.user_name);
		
	}
	public void logout(View v)
	{
		Intent goback =new Intent(this,MainActivity.class);
		startActivity(goback);
		finish();
	}
	public void onAttendanceClick(View v)
	{
		Intent i =new Intent(WelcomeWindow.this,AttendanceActivity.class);
		i.putExtra("user_name", userid);
		i.putExtra("password", password);
		startActivity(i);
		
	}
	public void onCourseClick(View v)
	{
		Intent i =new Intent(WelcomeWindow.this,CourseActivity.class);
		i.putExtra("user", userid);
		i.putExtra("pass", password);
		startActivity(i);
		
	}
	public void onStatClick(View v)
	{
		Intent i =new Intent(WelcomeWindow.this,ExportMain.class);
		i.putExtra("user_name", userid);
		i.putExtra("password", password);
		startActivity(i);
		
	}
	
	private void welcomeAlertDialog()
	{
		try {
			parsedresult= new JArrayIndex(jsonstring).setJObjectPosition(0);
			profession=parsedresult.getValue("rank");
			userid=parsedresult.getValue("id");
			password=parsedresult.getValue("password");
			name=parsedresult.getValue("name");
			department=parsedresult.getValue("department");
			viewAlertDialog(profession,department,name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@SuppressWarnings("deprecation")
	private void viewAlertDialog(String profession,String dept,String name)
	{
	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Welcome "+profession+"\n"+name+"\n\n"+"Department of "+dept+"\n")
		       .setTitle("Department of "+dept);
		AlertDialog dialog = builder.create();
		DialogInterface.OnClickListener listener =new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // User cancelled the dialog
	        	   dialog.dismiss();
	        	   
	           }
		};
		dialog.setButton("Ok", listener);
		
		

		dialog.show();
	}


}
