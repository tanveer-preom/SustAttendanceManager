package com.example.sustattendancemanager;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExportMain extends Activity implements LoadingClassListener{
	private List<String> courses;
	private ListView courseList;
	private String username,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export_course);
		username = getIntent().getExtras().getString("user_name");
		password =getIntent().getExtras().getString("password");
		initUi();
		new LoadingClass(StaticDatas.uri,this)
		.execute(new Key_value("user",username),
				new Key_value("pass",password),
				new Key_value("command","course"));
		
	}
	private void initUi()
	{
		courses = new LinkedList<String>();
		courseList = (ListView) findViewById(R.id.courseList);
	}
	private void initList()
	{
		Runnable r =new Runnable()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayAdapter<String> adapter =new ArrayAdapter<String>(ExportMain.this,android.R.layout.simple_list_item_1,courses);
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
		Log.i("Tanvy", jsonresult);
		try {
			JArrayIndex index =new JArrayIndex(jsonresult);
			int len =index.getLength();
			for(int i=0;i<len;i++)
			{
				index.setJObjectPosition(i);
				courses.add(index.getValue("title"));
				
			}
			initList();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
