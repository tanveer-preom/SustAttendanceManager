package com.example.sustattendancemanager;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class CourseActivity extends Activity implements OnClickListener,OnItemClickListener,LoadingClassListener{

	private Button addcourse;
	private ListView courselist;
	private ArrayAdapter<String> adapter;
	private List<String> courses;
	private String username,password,loaded_course;
	private AlertDialog alert=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		username=getIntent().getExtras().getString("user");
		password=getIntent().getExtras().getString("pass");
		setContentView(R.layout.course_window);
		initUi();
		new LoadingClass(StaticDatas.uri,this).execute(new Key_value("user",username),new Key_value("pass",password),new Key_value("command","course"));
		
	}
	private void initUi()
	{
		addcourse =(Button) findViewById(R.id.add_course);
		courselist=(ListView) findViewById(R.id.course_list);
		addcourse.setOnClickListener(this);
		courselist.setOnItemClickListener(this);
		courses= new LinkedList<String>();
		
	}
	private void initList()
	{
		
		Runnable r =new Runnable()
		{
			public void run()
			{
				adapter=new ArrayAdapter<String>(CourseActivity.this,android.R.layout.simple_list_item_1,courses);
				courselist.setAdapter(adapter);
			}
		};
		runOnUiThread(r);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId()==R.id.add_course)
		{
			
			AlertDialog.Builder alertdia = new AlertDialog.Builder(this);
			final EditText text =new EditText(CourseActivity.this);
			alertdia.setTitle("Course").setMessage("Enter Course Name");
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
	        LinearLayout.LayoutParams.MATCH_PARENT,
	        LinearLayout.LayoutParams.WRAP_CONTENT);
			text.setLayoutParams(lp);
			alertdia.setView(text);
			
			alertdia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int whichButton) {
			     //What ever you want to do with the value
			      loaded_course = text.getText().toString();
			      new LoadingClass(StaticDatas.uri,CourseActivity.this)
			      	.execute(new Key_value("user",username),
			    		  		new Key_value("pass",password),
			    		  		new Key_value("command","updatecourse"),
			    		  		new Key_value("title",loaded_course));
					
			      }
			    });

			    alertdia.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int whichButton) {
			        // what ever you want to do with No option.
			    	  alert.cancel();
			      }
			    });
			
			alert=alertdia.create();
			
				
				
				    
				
				alert.show();
		}
		//initList();
	}
	@Override
	public void onItemClick(AdapterView<?> list, View arg1, int pos, long id) {
		// TODO Auto-generated method stub
		Intent i =new Intent(CourseActivity.this,StudentList.class);
		i.putExtra("user_name", username);
		i.putExtra("password", password);
		i.putExtra("course_name", courses.get(pos));
		startActivity(i);
		
	}
	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return CourseActivity.this;
	}
	@Override
	public void onPostExecuted(String jsonresult) {
		// TODO After asynctask is finished
		if(jsonresult.compareTo("success")==0)
		{
			Runnable r=new Runnable()
			{
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(CourseActivity.this,"Course Loaded Successfully !!" , Toast.LENGTH_SHORT).show();
					courses.add(loaded_course);
					initList();
				}
			};
			runOnUiThread(r);
			return;
		}
		
		Log.i("Tanvy", jsonresult);
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
	
	

}
