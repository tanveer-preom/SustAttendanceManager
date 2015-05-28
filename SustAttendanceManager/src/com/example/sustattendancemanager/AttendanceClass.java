package com.example.sustattendancemanager;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AttendanceClass extends Activity implements OnClickListener,LoadingClassListener,OnItemClickListener {
	private Button addClass;
	private ListView classList;
	private String username,password,course_name;
	private List<String> classes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendance_course);
		username =getIntent().getExtras().getString("user_name");
		password=getIntent().getExtras().getString("password");
		course_name =getIntent().getExtras().getString("course_name");
		initUi();
		new LoadingClass(StaticDatas.uri,AttendanceClass.this).execute(
				new Key_value("user",username),
				new Key_value("pass",password),
				new Key_value("course_name",course_name),
				new Key_value("command","view_class")
				);
	}
	private void initUi()
	{
		addClass = (Button) findViewById(R.id.addClass);
		classList = (ListView) findViewById(R.id.class_list);
		classes = new LinkedList<String>();
		addClass.setOnClickListener(this);
		classList.setOnItemClickListener(this);
	}
	private void initList()
	{
		Runnable r =new Runnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayAdapter<String> adapter =new ArrayAdapter<String>(AttendanceClass.this,android.R.layout.simple_list_item_1,classes);
				classList.setAdapter(adapter);
			}
			
		};
		runOnUiThread(r);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String datetime=getTimeDate();
		new LoadingClass(StaticDatas.uri,AttendanceClass.this)
		.execute(new Key_value("user",username)
		,new Key_value("pass",password)
		,new Key_value("course_name",course_name)
		,new Key_value("command","add_class")
		,new Key_value("datetime",datetime)
		);
	}
	private String getTimeDate()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE_yyyy_MM_dd_HH_mm_ss");
		
		Calendar cal = Calendar.getInstance();
		Log.i("Tanvy",dateFormat.format(cal.getTime()));
		return dateFormat.format(cal.getTime());
	}
	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return this;
	}
	@Override
	public void onPostExecuted(String jsonresult) {
		// TODO Auto-generated method stub
		if(jsonresult.compareTo("success")==0)
		{
			Runnable r =new Runnable()
			{
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(AttendanceClass.this, "Class Created", Toast.LENGTH_SHORT).show();
					new LoadingClass(StaticDatas.uri,AttendanceClass.this).execute(
							new Key_value("user",username),
							new Key_value("pass",password),
							new Key_value("course_name",course_name),
							new Key_value("command","view_class")
					);
				
				}
				
			};
			runOnUiThread(r);
			return;
		}
		try {
			JArrayIndex index =new JArrayIndex(jsonresult);
			int len =index.getLength();
			classes.clear();
			for(int i=0;i<len;i++)
			{
				index.setJObjectPosition(i);
				classes.add(index.getValue("classes"));
			}
			initList();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		// TODO Auto-generated method stub
		Intent i =new Intent(AttendanceClass.this,AttendanceWindow.class);
		i.putExtra("user_name", username);
		i.putExtra("password", password);
		i.putExtra("course_name", course_name);
		i.putExtra("timestamp", classes.get(pos));
		startActivity(i);
		
	}

}
