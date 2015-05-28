package com.example.sustattendancemanager;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StudentList extends Activity implements LoadingClassListener,OnClickListener{
	private Button addStudent;
	private ListView studentList;
	private List<String> students;
	private String course_name,password,username;
	private AlertDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_list);
		username =getIntent().getExtras().getString("user_name");
		password=getIntent().getExtras().getString("password");
		course_name=getIntent().getExtras().getString("course_name");
		initUi();
		//Log.i("Tanvy",course_name);
		new LoadingClass(StaticDatas.uri,this).execute(
				new Key_value("user",username),
				new Key_value("pass",password),
				new Key_value("command","loadcourse"),
				new Key_value("course_name",course_name)
				);
		
	}
	private void initUi()
	{
		addStudent =(Button) findViewById(R.id.add_student);
		studentList =(ListView) findViewById(R.id.student_list);
		students = new LinkedList<String>();
		addStudent.setOnClickListener(this);
	}
	private void initList()
	{
		Runnable r =new Runnable()
		{
			
			public void run()
			{
				ArrayAdapter<String> adapter =new ArrayAdapter<String>(StudentList.this,android.R.layout.simple_list_item_1,students);
				studentList.setAdapter(adapter);
			}
			
		};
		runOnUiThread(r);
	}
	@Override
	public Activity getContext() {
		// TODO Auto-generated method stub
		return StudentList.this;
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
					Toast.makeText(StudentList.this, "added successfully", Toast.LENGTH_SHORT).show();
					new LoadingClass(StaticDatas.uri,StudentList.this).execute(
							new Key_value("user",username),
							new Key_value("pass",password),
							new Key_value("command","loadcourse"),
							new Key_value("course_name",course_name)
							);
				}
				
				
			};
			runOnUiThread(r);
			
			return;
		}
		
		Log.i("Tanvy", jsonresult);
		try {
			JArrayIndex index =new JArrayIndex(jsonresult).setJObjectPosition(0);
			int len=index.getLength();
			students.clear();
			for(int i=0;i<len;i++)
			{
				index.setJObjectPosition(i);
				students.add(index.getValue("s_name"));
				
			}
			initList();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.add_student)
		{
			showStudentDialog();
		}
		
	}
	private void showStudentDialog()
	{
		//Dialog dialog =new Dialog(this);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add Student");
		LinearLayout outerlayout= new LinearLayout(this);
		outerlayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		//dialog.addContentView(outerlayout,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		outerlayout.setOrientation(LinearLayout.VERTICAL);
		//dialog.setTitle("Add Student");
		TextView view =new TextView(this);
		view.setText("Enter Name :");
		outerlayout.addView(view,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		//a.addContentView(l,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		final EditText name = new EditText(this);
		outerlayout.addView(name,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		view =new TextView(this);
		view.setText("Enter Reg-Number :");
		outerlayout.addView(view,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		final EditText reg = new EditText(this);
		outerlayout.addView(reg,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		/*LinearLayout buttonlayout=new LinearLayout(this);
		buttonlayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		buttonlayout.setOrientation(LinearLayout.HORIZONTAL);
		Button cancel =new Button(this);
		cancel.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		cancel.setText("Cancel");
		buttonlayout.addView(cancel);
		Button ok = new Button(this);
		ok.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		ok.setText("Ok");
		buttonlayout.addView(ok);
		outerlayout.addView(buttonlayout);*/
		builder.setView(outerlayout);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String s_name = name.getText().toString();
				String reg_num = reg.getText().toString();
				new LoadingClass(StaticDatas.uri,StudentList.this).execute(
						new Key_value("user",username),
						new Key_value("pass",password),
						new Key_value("command","insert_student"),
						new Key_value("course_name",course_name),
						new Key_value("s_name",s_name),
						new Key_value("reg_number",reg_num)
						);
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});
		dialog =builder.create();
		//a.addContentView(reg,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		dialog.show();
		
	}
	
	
}
