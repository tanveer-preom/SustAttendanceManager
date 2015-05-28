package com.example.sustattendancemanager;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AttendanceWindow extends Activity implements LoadingClassListener,UpdateAttendanceListener{
	private List<NameRegAttend> students;
	private ListView studentList;
	private String username,password,courseName,date;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendance_window);
		initUi();
		username =getIntent().getExtras().getString("user_name");
		password=getIntent().getExtras().getString("password");
		courseName=getIntent().getExtras().getString("course_name");
		date=getIntent().getExtras().getString("timestamp");
		new LoadingClass(StaticDatas.uri,this).execute(
				new Key_value("user",username),
				new Key_value("pass",password),
				new Key_value("command","new_class"),
				new Key_value("course_name",courseName),
				new Key_value("date",date)
				);
	}
	private void initUi()
	{
		students =new LinkedList<NameRegAttend>();
		studentList = (ListView) findViewById(R.id.attendanceList);
		
	}
	private void initList()
	{
		Runnable r =new Runnable()
		{

			@Override
			public void run() {
				Log.i("Tanvy", "inside run");
				String[] arr =new String[students.size()];
				for(int i=0;i<students.size();i++)
				{
					arr[i] =students.get(i).getReg();
					
				}
				
				CustomAdapter_attendance adapter =new CustomAdapter_attendance(AttendanceWindow.this,students,arr,username,password);
				studentList.setAdapter(adapter);
			}
		};
		runOnUiThread(r);
		
	}

	

	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void onPostExecuted(String jsonresult) {
		// TODO Auto-generated method stub
		Log.i("Tanvy", jsonresult);
		try {
			JArrayIndex index =new JArrayIndex(jsonresult);
			int len= index.getLength();
			for(int i=0;i<len;i++)
			{
				index.setJObjectPosition(i);
				String name= index.getValue("s_name"),reg_num=index.getValue("reg_id"),classdate=index.getValue(date);
				students.add(new NameRegAttend(name,reg_num,classdate));
				
			}
			initList();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public synchronized void getErrorMessage(Exception e) {
		// TODO Auto-generated method stub
		Log.i("Tanvy", "error in update thread "+e.getMessage());
		
	}
	@Override
	public String attendanceNumber(boolean isPresent) {
		// TODO Auto-generated method stub
		if(isPresent)
		return "1";
		else
		return "0";
	}
	@Override
	public Context classContext() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public Key_value[] getKeyVal(String attendance,String reg_no) {
		// TODO Auto-generated method stub
		Key_value[] keys =new Key_value[7];
		keys[0] =new Key_value("user",username);
		keys[1] = new Key_value("pass",password);
		keys[2] =new Key_value("command","update_class");
		keys[3] =new Key_value("date",date);
		keys[4] =new Key_value("attendance",attendance);
		keys[5] =new Key_value("reg_id",reg_no);
		keys[6] =new Key_value("course_name",courseName);
		return keys;
		
	}
	@Override
	public NameRegAttend objectToModify(int pos) {
		// TODO Auto-generated method stub
		return students.get(pos);
	}
	@Override
	public String getUri() {
		// TODO Auto-generated method stub
		return StaticDatas.uri;
	}
	
	

}
