package com.example.sustattendancemanager;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AttendanceActivity extends Activity implements LoadingClassListener,OnItemClickListener{
		private String username,password;
		private ListView courseList;
		private List<String> courses;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.attendance_activity);
			username=getIntent().getExtras().getString("user_name");
			password=getIntent().getExtras().getString("password");
			initUi();
			new LoadingClass(StaticDatas.uri,this)
			.execute(new Key_value("user",username),
					new Key_value("pass",password),
					new Key_value("command","course"));
		}
		private void initUi()
		{
			courseList =(ListView) findViewById(R.id.course_list);
			courses =new LinkedList<String>();
			courseList.setOnItemClickListener(this);
		}
		private void initList()
		{
			Runnable r=new Runnable()
			{

				@Override
				public void run() {
					// TODO Auto-generated method stub
					ArrayAdapter<String> adapter =new ArrayAdapter<String>(AttendanceActivity.this,android.R.layout.simple_list_item_1,courses);
					courseList.setAdapter(adapter);
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
			try {
				JArrayIndex index =new JArrayIndex(jsonresult).setJObjectPosition(0);
				int len =index.getLength();
				for(int i=0 ; i<len;i++)
				{
					index.setJObjectPosition(i);
					courses.add(index.getValue("title"));
					//Log.i("Tanvy", courses.get(i));
					
				}
				initList();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.i("Tanvy", "JSON "+e.getMessage());
				e.printStackTrace();
			}
			
		}
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long arg3) {
			// TODO Auto-generated method stub
			Intent i =new Intent(AttendanceActivity.this,AttendanceClass.class);
			i.putExtra("user_name", username);
			i.putExtra("password", password);
			i.putExtra("course_name", courses.get(pos));
			startActivity(i);
			
		}
		
}
